package com.happy;

import com.happy.domain.Response;
import oracle.jdbc.OracleCallableStatement;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;
import oracle.sql.STRUCT;
import oracle.sql.StructDescriptor;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class JDBCTest {
    public static void main(String[] args) {
        Connection conn = null;
        CallableStatement stmt = null;

        int backVal = -1;

        try {
            conn = DriverManager.getConnection("jdbc:oracle:thin:@10.25.160.20:1521:CN76C1BD", "app_dd", "Testbus!654321");

            if (conn != null) {
                stmt = conn.prepareCall("{call PKG_DD_FOR_PSBC.PROC_AUTO_IMPORT_RES_FROM_FILE(?, ?)}");
                ARRAY adArray = getOracleArray(conn, "DDRES_TYP_OBJ_TAB", getInputResponseList());
                ((OracleCallableStatement) stmt).setARRAY(1, adArray);
                stmt.registerOutParameter(2, java.sql.Types.INTEGER);
                stmt.execute();
                backVal = stmt.getInt(2);

                System.out.println(backVal);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private static ARRAY getOracleArray(Connection con, String oraclelist,
                                        List objlist) throws Exception {
        ARRAY list = null;
        if (objlist != null && objlist.size() > 0) {
            StructDescriptor structDesc = new StructDescriptor("DDRES_TYP_OBJ", con);
            STRUCT[] structs = new STRUCT[objlist.size()];
            Object[] result = new Object[0];
            for (int i = 0; i < objlist.size(); i++) {
                result = new Object[10];
                Response t = (Response) (objlist.get(i));

                result[0] = t.getId();
                System.out.println(t.getInsertTime());
                System.out.println(System.currentTimeMillis());
                result[1] = t.getInsertTime();
                result[2] = t.getReturnCode();
                result[3] = t.getDdFlag();
                result[4] = t.getDdFlagBatch();
                result[5] = t.getDdSign();
                result[6] = t.getResultInfo();
                result[7] = t.getSourceRowIdx();
                result[8] = t.getIdCategory();
                result[9] = t.getPairedToDdRequest();
                /*
                 * 一定要记得导入orai18n.jar
                 * 否则一遇到字符串就乱码、添加不到数据
                 */
                structs[i] = new STRUCT(structDesc, con, result);
            }

            ArrayDescriptor desc = ArrayDescriptor.createDescriptor(oraclelist,
                    con);
            list = new ARRAY(desc, con, structs);
        } else {
            ArrayDescriptor desc = ArrayDescriptor.createDescriptor(oraclelist,
                    con);
            STRUCT[] structs = new STRUCT[0];
            list = new ARRAY(desc, con, structs);
        }
        return list;
    }

    private static List<Response> getInputResponseList() {
        List<Response> list = new ArrayList<Response>();
        Timestamp time = new Timestamp(new Date().getTime());
        Response response1 = new Response();
        response1.setId(1026678658);
        response1.setInsertTime(time);
        response1.setReturnCode("0000");
        response1.setResultInfo("SUCCESS");
        response1.setSourceRowIdx(1);

        Response response2 = new Response();
        response2.setId(1026678659);
        response2.setInsertTime(time);
        response2.setReturnCode("0001");
        response2.setResultInfo("FAILED");
        response2.setSourceRowIdx(1);

        Response response3 = new Response();
        response3.setId(1026678660);
        response3.setInsertTime(time);
        response3.setReturnCode("0002");
        response3.setResultInfo("RETURN");
        response3.setSourceRowIdx(1);

        Response response4 = new Response();
        response4.setId(1026678661);
        response4.setInsertTime(time);
        response4.setReturnCode("0003");
        response4.setResultInfo("UNSUCCESS");
        response4.setSourceRowIdx(1);

        Response response5 = new Response();
        response5.setId(1026678662);
        response5.setInsertTime(time);
        response5.setReturnCode("0004");
        response5.setResultInfo("PROCESSING");
        response5.setSourceRowIdx(1);

        list.add(response1);
        list.add(response2);
        list.add(response3);
        list.add(response4);
        list.add(response5);

        return list;
    }
}
