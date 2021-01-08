package com.example.pristine_approval_portal.globalization;

import android.content.Context;

import com.example.pristine_approval_portal.sql_lite_process.dao.ExceptionDao;
import com.example.pristine_approval_portal.sql_lite_process.database.PristineDatabase;
import com.example.pristine_approval_portal.sql_lite_process.model.ExceptionTableModel;

import java.io.PrintWriter;
import java.io.StringWriter;

import static com.example.pristine_approval_portal.sql_lite_process.database.PristineDatabase.databaseWriteExecutor;


public class ApiRequestFailure {
    public static void PostExceptionToServer(Throwable t, String Class_fragment_Name, String flag, Context context) {
        try {
            StringWriter sw = new StringWriter();
            t.printStackTrace(new PrintWriter(sw));
            String exceptionAsString = sw.toString();
            databaseWriteExecutor.execute(() -> {
                try {
                    PristineDatabase db = PristineDatabase.getAppDatabase(context);
                    ExceptionDao dao = db.exceptionDao();
                       ExceptionTableModel model = new ExceptionTableModel();
                       model.setMyException(exceptionAsString);
                       model.setExceptionType(t.getClass().getCanonicalName());
                       model.setLineNo(String.valueOf(t.getStackTrace()[0].getLineNumber()));
                       model.setFragment(Class_fragment_Name);
                       model.setMethod(flag);
                       long a = dao.insert(model);
                    db.close();
                }catch (Exception e){
                }
            });
        } catch (Exception e) {
        }

    }
}
