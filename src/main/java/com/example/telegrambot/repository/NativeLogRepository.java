package com.example.telegrambot.repository;

import com.example.telegrambot.api.model.HistorySearchParams;
import com.example.telegrambot.utils.NativeSqlHelper;
import com.example.telegrambot.utils.Operator;
import jakarta.persistence.EntityManager;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Repository;

@Repository
public class NativeLogRepository {

    private final EntityManager em;

    public NativeLogRepository(EntityManager em) {
        this.em = em;
    }

    public Page<BotLog> getLogHistory(HistorySearchParams params, @Nullable Long user_id) {
        NativeSqlHelper sql = new NativeSqlHelper("bot_log");

        if (params.getFilterFrom() != null) {
            sql.addWhereClause("request_time", Operator.GE, params.getFilterFrom());
        }
        if (params.getFilterTo() != null) {
            sql.addWhereClause("request_time", Operator.LE, params.getFilterTo());
        }
        if (user_id != null) {
            sql.addWhereClause("user_id", Operator.EQ, user_id);
        }

        var query = em.createNativeQuery(sql.toString(), BotLog.class);
        if (params.getPageNumber() != null && params.getPageSize() != null) {
            query.setFirstResult((params.getPageNumber()) * params.getPageSize());
            query.setMaxResults(params.getPageSize());
        }

        Integer totalPages = NativeSqlHelper.getTotalPages(((Long) em.createNativeQuery(sql.getTotalQuery()).getSingleResult()).intValue(), params.getPageSize());
        return new Page<>(query.getResultList(), totalPages);
    }
}
