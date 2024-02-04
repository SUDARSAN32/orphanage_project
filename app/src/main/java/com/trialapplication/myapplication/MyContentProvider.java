package com.trialapplication.myapplication;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

public class MyContentProvider extends ContentProvider {

    public static final String AUTHORITY = "com.trialapplication.myapplication.provider";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/content");

    private static final int CONTENT = 1;
    private static final UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, "content", CONTENT);
    }

    @Override
    public boolean onCreate() {
        // Initialization, if needed
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        // Handle query operations, if needed
        return null;
    }

    @Override
    public String getType(Uri uri) {
        return "vnd.android.cursor.dir/vnd." + AUTHORITY + ".content";
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        int match = uriMatcher.match(uri);
        Log.d("MyContentProvider", "insert method called with URI: " + uri.toString());
        switch (match) {
            case CONTENT:

                String insertedContent = values.getAsString("content");
                Log.d("MyContentProvider", "Inserted content: " + insertedContent);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        return uri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Handle delete operations, if needed
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        // Handle update operations, if needed
        return 0;
    }
}
