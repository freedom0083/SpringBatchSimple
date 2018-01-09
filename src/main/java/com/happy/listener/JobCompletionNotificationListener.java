package com.happy.listener;

import com.happy.domain.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {
    private static Logger logger = LoggerFactory.getLogger(JobCompletionNotificationListener.class);
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JobCompletionNotificationListener(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            logger.info("!!! JOB FINISHED! Time to verify the results");

            List<Response> results = jdbcTemplate.query("SELECT PAYER_ACC_NAME, PAYER_BANK_NAME FROM people", new RowMapper<Response>() {
                @Override
                public Response mapRow(ResultSet rs, int row) throws SQLException {
                    return new Response(rs.getString(1), rs.getString(2));
                }
            });

            for (Response response : results) {
                logger.info("Found <" + response.getPayerAccName() + ", " + response.getPayerBankName() + "> in the database.");
            }

        }
    }
}
