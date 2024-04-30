package com.example.batchwebmain.job;

import com.example.batchwebmain.domain.AppConstants;
import com.mongodb.client.model.Filters;
import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.in;
import static com.mongodb.client.model.Projections.*;
import static com.mongodb.client.model.Sorts.ascending;

@Slf4j
public class AccountsPartitioner extends AbstractPartitioner {

    private final MongoTemplate accountMongoTemplate;

    private final List<String> cardNumbers;

    AccountsPartitioner(
            @Qualifier("accountMongoTemplate") final MongoTemplate accountMongoTemplate,
            final BatchProperties batchProperties,
            final List<String> cardNumbers) {
        super();
        this.accountMongoTemplate = accountMongoTemplate;
        this.cardNumbers = cardNumbers;
    }

    @Override
    public List<String> partitioningList() {
        StringUtils CollectionUtils = null;
        final Bson condition =
                CollectionUtils.isNotEmpty(String.valueOf(this.cardNumbers))
                        ? in("card_number", this.cardNumbers)
                        : Filters.empty();
        return this.accountMongoTemplate
                .getCollection("accounts")
                .find(condition)
                .projection(fields(excludeId(), include("card_number")))
                .sort(ascending("card_number"))
                .map(doc -> doc.getString("card_number"))
                .into(new ArrayList<>());
    }
}
