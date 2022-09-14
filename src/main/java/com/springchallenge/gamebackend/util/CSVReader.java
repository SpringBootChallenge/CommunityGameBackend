package com.springchallenge.gamebackend.util;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import com.springchallenge.gamebackend.exception.ExceptionType;
import com.springchallenge.gamebackend.exception.ExceptionsGenerator;
import com.springchallenge.gamebackend.model.Game;

@Component
public class CSVReader {
    @Autowired
    private ResourceLoader resourceLoader;
    private static final String CLASSPATH_URL = "classpath:";
    private static final String DEFAULT_FILE_URL = "data/games.csv";
    private final Logger logger = LoggerFactory.getLogger(CSVReader.class);

    private Iterable<CSVRecord> loadCsvFromClassPath(String url) {
        Resource fileToLoad = resourceLoader.getResource(CLASSPATH_URL + url);
        CSVFormat csvFormat = CSVFormat.DEFAULT
                .builder()
                .setHeader()
                .setNullString("")
                .setSkipHeaderRecord(true)
                .build();
        try (CSVParser parser = new CSVParser(
                new InputStreamReader(fileToLoad.getInputStream(), StandardCharsets.UTF_8), csvFormat)) {
            return parser.getRecords();

        } catch (IOException e) {
            logger.error("The read operation in the file was interrupted", e);
            throw ExceptionsGenerator.getException(ExceptionType.INVALID_FILE,
                    "The read operation on the file could not be completed.");
        }
    }

    private List<Game> csvRowsToGames(Iterable<CSVRecord> csvRecords) {
        List<Game> games = new ArrayList<>();
        for (CSVRecord csvRecord : csvRecords) {
            Game newGame = new Game();
            newGame.setTitle(csvRecord.get("Name"));
            newGame.setImage(csvRecord.get("Image URL"));
            newGame.setDescription(csvRecord.get("Description"));
            String releaseDateTxt = csvRecord.get("Release date");
            if (releaseDateTxt != null) {
                newGame.setReleaseDate(LocalDate.parse(releaseDateTxt));
            }
            newGame.setGenre(csvRecord.get("Genre"));
            newGame.setPlatform(csvRecord.get("Platform"));
            games.add(newGame);
        }
        return games;
    }

    public List<Game> loadGamesFromCsv() {
        Iterable<CSVRecord> csvRecords = loadCsvFromClassPath(DEFAULT_FILE_URL);
        return csvRowsToGames(csvRecords);
    }

}
