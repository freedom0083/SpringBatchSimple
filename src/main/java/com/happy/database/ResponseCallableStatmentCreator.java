package com.happy.database;

import com.happy.domain.Response;
import oracle.jdbc.OracleCallableStatement;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;
import oracle.sql.STRUCT;
import oracle.sql.StructDescriptor;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.stereotype.Component;


import java.sql.*;
import java.util.List;

@Component
public class ResponseCallableStatmentCreator implements CallableStatementCreator {
    private static final String ORACLE_RESPONSE_PARAM_LIST = "DDRES_TYP_OBJ_TAB";
    private List<? extends Response> responseList;

    @Override
    public CallableStatement createCallableStatement(Connection con) throws SQLException {
        String callProcedure = "{call PKG_DD_FOR_PSBC.PROC_AUTO_IMPORT_RES_FROM_FILE(?, ?)}";

        CallableStatement cs = con.prepareCall(callProcedure);

        ARRAY adArray = getArray(con, responseList);
        ((OracleCallableStatement) cs).setARRAY(1, adArray);
        cs.registerOutParameter(2, java.sql.Types.INTEGER);

        return cs;
    }

    private static ARRAY getArray(Connection con,
                                  List<? extends Response> responseList) throws SQLException {
        ARRAY list;
        STRUCT[] structArray;
        if (responseList != null && responseList.size() > 0) {
            StructDescriptor structDesc = new StructDescriptor("DDRES_TYP_OBJ", con.unwrap(oracle.jdbc.OracleConnection.class));

            structArray = new STRUCT[responseList.size()];
            Object[] result;

            for (int i = 0; i < responseList.size(); i++) {
                result = new Object[10];
                Response response = responseList.get(i);

                result[0] = response.getId();
                result[1] = response.getInsertTime();
                result[2] = response.getReturnCode();
                result[3] = response.getDdFlag();
                result[4] = response.getDdFlagBatch();
                result[5] = response.getDdSign();
                result[6] = response.getResultInfo();
                result[7] = response.getSourceRowIdx();
                result[8] = response.getIdCategory();
                result[9] = response.getPairedToDdRequest();
                /*
                 * 一定要记得导入orai18n.jar
                 * 否则一遇到字符串就乱码、添加不到数据
                 */
                structArray[i] = new STRUCT(structDesc, con, result);
            }

            ArrayDescriptor desc = ArrayDescriptor.createDescriptor(ORACLE_RESPONSE_PARAM_LIST,
                    con);
            list = new ARRAY(desc, con, structArray);
        } else {
            ArrayDescriptor desc = ArrayDescriptor.createDescriptor(ORACLE_RESPONSE_PARAM_LIST,
                    con);
            structArray = new STRUCT[0];
            list = new ARRAY(desc, con, structArray);
        }

        return list;
    }

    public void setResponseList(List<? extends Response> responseList) {
        this.responseList = responseList;
    }
}


