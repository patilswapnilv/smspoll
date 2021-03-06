package com.novoda.smspoll.contentprovider;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.test.AndroidTestCase;

import com.novoda.smspoll.R;
import com.novoda.smspoll.contentprovider.Provider;
import com.novoda.smspoll.util.DBUtil;

public class ProviderTest extends AndroidTestCase {

	private SQLiteDatabase			sqliteDb;
	private DBUtil					dbUtil					= null;

	private static final Uri		QUERY_ALL_POLLS			= Uri.parse("content://com.novoda.smspoll/polls");
	private static final String[]	ALL_POLL_COLS			= new String[] {
			Provider.Poll._ID, Provider.Poll.QUESTION, Provider.Poll.STARTDATE, Provider.Poll.ENDDATE
															};
	private static final Uri		QUERY_ANSWER_FOR_POLL	= Uri.parse("content://com.novoda.smspoll/polls/1/answers");

	protected void setUp() {
		getContext().getContentResolver().query(QUERY_ALL_POLLS, null, null, null, null).close();

		if (dbUtil == null) {
			dbUtil = new DBUtil(getContext(), Provider.DATABASE_NAME);
		}
		// insert initial values
		dbUtil.executeDB(R.raw.db_insert_poll1);
		sqliteDb = dbUtil.getDatabase();
		dbUtil.getDatabase().close();

	}

	@Override
	protected void tearDown() throws Exception {
		if (dbUtil == null) {
			dbUtil = new DBUtil(getContext(), Provider.DATABASE_NAME);
		}
		dbUtil.executeDB(R.raw.db_delete_all_values);
		sqliteDb.close();
		super.tearDown();
	}

	public void testQueryingAllPolls() throws Exception {
		Cursor cur = getContext().getContentResolver().query(QUERY_ALL_POLLS, ALL_POLL_COLS, null, null, null);
		assertNotNull(cur);
		// assertEquals(1, cur.getCount());
	}

	public void testQueryingAnswerForPoll() throws Exception {
		Cursor cur = getContext().getContentResolver().query(QUERY_ANSWER_FOR_POLL, null, null, null, null);
		assertNotNull(cur);
		// assertEquals(1, cur.getCount());
	}

}
