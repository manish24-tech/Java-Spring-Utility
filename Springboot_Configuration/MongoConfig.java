package com.fourbench.schoolmanagementservice.configuration;

import org.bson.Document;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Spring Data MongoDB will automatically register the ZonedDateTimeReadConverter, and ZonedDateTimeWriteConverter to handle the conversion between ZonedDateTime and the BSON representation stored in MongoDB.
 */
@Configuration
public class MongoConfig {
    @Bean
    public MongoCustomConversions mongoCustomConversions() {
        List<Converter<?, ?>> converters = new ArrayList<>();
        converters.add(new ZonedDateTimeReadConverter());
        converters.add(new ZonedDateTimeWriteConverter());

        return new MongoCustomConversions(converters);
    }

    public class ZonedDateTimeReadConverter implements Converter<Document, ZonedDateTime> {

        @Override
        public ZonedDateTime convert(Document document) {
            // Retrieve the relevant fields from the Document
            Date date = document.get("timestamp", Date.class);
            Instant instant = date.toInstant();
            ZoneId zoneId = ZoneId.of(document.getString("timezone"));

            // Create the ZonedDateTime object
            return ZonedDateTime.ofInstant(instant, zoneId);
        }
    }

    public class ZonedDateTimeWriteConverter implements Converter<ZonedDateTime, Document> {

        @Override
        public Document convert(ZonedDateTime zonedDateTime) {
            // Extract the relevant fields from the ZonedDateTime object
            Instant instant = zonedDateTime.toInstant();
            String timezone = zonedDateTime.getZone().getId();

            // Create the Document with the extracted fields
            Document document = new Document();
            document.put("timestamp", instant);
            document.put("timezone", timezone);
            return document;
        }
    }
}