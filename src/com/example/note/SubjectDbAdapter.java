package com.example.note;



import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;



public class SubjectDbAdapter {

	public static final String KEY_ROWID = "subj_id";
	public static final String KEY_SUBJECTNAME = "subjectname";
	public static final String KEY_DATE = "date";
	
	 private static final String TAG = "NotesDbAdapter";
	    private DatabaseHelper mDbHelper;
	    private SQLiteDatabase mDb;
	    
	    private static final String DATABASE_CREATE =
	            "create table notes (_id integer primary key autoincrement, "
	            + "title text not null, subject text not null, body text not null, date text not null);";

	        private static final String DATABASE_NAME = "data";
	        private static final String DATABASE_TABLE = "Subject";
	        private static final int DATABASE_VERSION = 2;

	        private final Context mCtx;

	        private static class DatabaseHelper extends SQLiteOpenHelper {

	            DatabaseHelper(Context context) {
	                super(context, DATABASE_NAME, null, DATABASE_VERSION);
	            }

	            @Override
	            public void onCreate(SQLiteDatabase db) {

	                db.execSQL(DATABASE_CREATE);
	            }

	            @Override
	            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	                Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
	                        + newVersion + ", which will destroy all old data");
	                db.execSQL("DROP TABLE IF EXISTS notes");
	                onCreate(db);
	            }
	        }

	     
	        public SubjectDbAdapter(Context ctx) {
	            this.mCtx = ctx;
	        }

	      
	        public SubjectDbAdapter open() throws SQLException {
	            mDbHelper = new DatabaseHelper(mCtx);
	            mDb = mDbHelper.getWritableDatabase();
	            return this;
	        }

	        public void close() {
	            mDbHelper.close();
	        }


	        public long createSubject(String subjectname, String date) {
	            ContentValues initialValues = new ContentValues();
	            initialValues.put(KEY_SUBJECTNAME, subjectname);
	            initialValues.put(KEY_DATE, date);

	            return mDb.insert(DATABASE_TABLE, null, initialValues);
	        }

	    
	        public boolean deleteSubject(long rowId) {

	            return mDb.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
	        }

	     
	        public Cursor fetchAllSubject() {

	            return mDb.query(DATABASE_TABLE, new String[] {KEY_ROWID, KEY_SUBJECTNAME,KEY_DATE}, null, null, null, null, null);
	        }

	        public Cursor fetchSubject(long rowId) throws SQLException {

	            Cursor mCursor =

	                mDb.query(true, DATABASE_TABLE, new String[] {KEY_ROWID,
	                        KEY_SUBJECTNAME,KEY_DATE}, KEY_ROWID + "=" + rowId, null,
	                        null, null, null, null);
	            if (mCursor != null) {
	                mCursor.moveToFirst();
	            }
	            return mCursor;

	        }

	
	        public boolean updateSubject(long rowId,String subjectname,String date) {
	            ContentValues args = new ContentValues();
	            args.put(KEY_SUBJECTNAME, subjectname);
	            args.put(KEY_DATE, date);
	            
	            return mDb.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
	        }
}
