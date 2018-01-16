package com.happy.database.handler;

import com.happy.domain.Response;
import oracle.jdbc.OracleConnection;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;
import oracle.sql.STRUCT;
import oracle.sql.StructDescriptor;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.List;

@Component
public class OracleArrayTypeHandler extends BaseTypeHandler<List<? extends Response>> {
    private static final String ORACLE_RESPONSE_PARAM_LIST = "DDRES_TYP_OBJ_TAB";
    public static final String ORACLE_TYPE_OBJECT = "DDRES_TYP_OBJ";

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, List<? extends Response> list, JdbcType jdbcType) throws SQLException {
        if (null != list) {
            ARRAY array = getArray(ps.getConnection(), list);
            ps.setArray(i, array);
        }

    }

    @Override
    public List<? extends Response> getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return null;
    }

    @Override
    public List<? extends Response> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return null;
    }

    @Override
    public List<? extends Response> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return null;
    }

    /**
     * According to the requirement,
     * will change return code to 0000 if return code is BF00338,
     * and change return code to 3008 if return code is BF00103
     *
     * @param responseCode
     * @return
     */
    private String getReturnCode(String responseCode) {
        String returnCode = responseCode;
        switch (responseCode) {
            case "BF00338":
                returnCode = "0000";
                break;
            case "BF00103":
                returnCode = "3008";
                break;

            default:
                break;
        }
        return returnCode;
    }

    /**
     * According to the requirement,
     * will change return result message to 交易成功 if return code is BF00338,
     * and change return result message to 余额不足 if return code is BF00103
     *
     * @param responseCode
     * @param resultInfo
     * @return
     */
    private String getResultInfo(String responseCode, String resultInfo) {
        String returnResultInfo = resultInfo;

        switch (responseCode) {
            case "BF00338":
                returnResultInfo = "交易成功";
                break;
            case "BF00103":
                returnResultInfo = "余额不足";
                break;

            default:
                break;
        }

        return returnResultInfo;

    }

    /**
     * Covert list to Oracle ARRAY
     *
     * @param con
     * @param responseList
     * @return
     * @throws SQLException
     */
    private ARRAY getArray(Connection con,
                           List<? extends Response> responseList) throws SQLException {
        ARRAY list;
        STRUCT[] structArray;
        if (responseList != null && responseList.size() > 0) {
            StructDescriptor structDesc = new StructDescriptor(ORACLE_TYPE_OBJECT, con.unwrap(OracleConnection.class));

            structArray = new STRUCT[responseList.size()];
            Object[] result;

            for (int i = 0; i < responseList.size(); i++) {
                result = new Object[10];
                Response response = responseList.get(i);

                result[0] = response.getId();
                result[1] = response.getInsertTime();
                result[2] = getReturnCode(response.getReturnCode());
                result[3] = response.getDdFlag();
                result[4] = response.getDdFlagBatch();
                result[5] = response.getDdSign();
                result[6] = getResultInfo(response.getReturnCode(), response.getResultInfo());
                result[7] = response.getSourceRowIdx();
                result[8] = response.getIdCategory();
                result[9] = response.getPairedToDdRequest();

                structArray[i] = new STRUCT(structDesc, con.unwrap(OracleConnection.class), result);
            }

            ArrayDescriptor desc = ArrayDescriptor.createDescriptor(ORACLE_RESPONSE_PARAM_LIST,
                    con.unwrap(OracleConnection.class));
            list = new ARRAY(desc, con.unwrap(OracleConnection.class), structArray);
        } else {
            ArrayDescriptor desc = ArrayDescriptor.createDescriptor(ORACLE_RESPONSE_PARAM_LIST,
                    con.unwrap(OracleConnection.class));
            structArray = new STRUCT[0];
            list = new ARRAY(desc, con.unwrap(OracleConnection.class), structArray);
        }

        return list;
    }
}
