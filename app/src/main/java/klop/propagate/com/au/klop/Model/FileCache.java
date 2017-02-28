package klop.propagate.com.au.klop.Model;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by trung on 22/12/2016.
 */
public class FileCache {
    private static final String LOG_TAG = "Hop" ;
    private File cacheDir;

    public FileCache(Context context){
        //Find the dir to save cached images
        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
            cacheDir = new File(android.os.Environment.getExternalStorageDirectory(),"LazyList");
            cacheDir.mkdir();
        }
        else {
            cacheDir = context.getCacheDir();
        }
        if(!cacheDir.exists())
            Log.e(LOG_TAG, "Directory not created");
            cacheDir.mkdir();
    }

    public File getFile(String url){
        //I identify images by hashcode. Not a perfect solution, good for the demo.
        String filename=String.valueOf(url.hashCode());
        //Another possible solution (thanks to grantland)
        //String filename = URLEncoder.encode(url);
        File f = new File(cacheDir, filename);
        return f;

    }

    public void clearcache(){
        File[] files=cacheDir.listFiles();
        if(files==null)
            return;
        for(File f:files) {
            f.delete();
        }
    }
    public static void CopyStream(InputStream is, OutputStream os) {
        final int buffer_size = 1024;
        try {
            byte[] bytes = new byte[buffer_size];
            for (;;) {
                int count = is.read(bytes, 0, buffer_size);
                if (count == -1)
                    break;
                os.write(bytes, 0, count);
            }
        } catch (Exception ex) {
        }
    }
}
