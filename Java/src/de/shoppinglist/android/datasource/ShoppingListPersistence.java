package de.shoppinglist.android.datasource;

import de.shoppinglist.android.bean.Shoppinglist;
import de.shoppinglist.android.constant.DBConstants;

public class ShoppingListPersistence {
	
	private ShoppinglistDataSource datasource;
	
	public ShoppingListPersistence(ShoppinglistDataSource datasource){
		this.datasource = datasource;
	}
	
	public void add(Shoppinglist shoppinglist){
		datasource.isDbLockedByThread();
		
		// then insert a new one
		final String sqlInsertNew = "INSERT INTO " + DBConstants.TAB_SHOPPINGLIST_NAME + " ("
				+ DBConstants.COL_SHOPPINGLIST_CREATED_TIME + ") VALUES (CURRENT_TIMESTAMP)";

		datasource.getDatabase().execSQL(sqlInsertNew);
	}
	
	public void update(Shoppinglist shoppinglist){
		datasource.isDbLockedByThread();

		// at first set the old shoppinglist to finished (current_timestamp)
		final String sqlMarkShoppinglistFinished = "UPDATE " + DBConstants.TAB_SHOPPINGLIST_NAME
				+ " SET " + DBConstants.COL_SHOPPINGLIST_FINISHED_TIME
				+ " = CURRENT_TIMESTAMP WHERE " + DBConstants.COL_SHOPPINGLIST_ID
				+ " = (SELECT MAX(" + DBConstants.COL_SHOPPINGLIST_ID + ") AS "
				+ DBConstants.COL_SHOPPINGLIST_ID + " FROM " + DBConstants.TAB_SHOPPINGLIST_NAME
				+ ")";

		datasource.getDatabase().execSQL(sqlMarkShoppinglistFinished);
	}
}
