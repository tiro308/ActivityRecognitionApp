package com.example.atrt01admin.meinexample03;

public class CodeGraveyard {

    //from MyDBHandler
    //    //print db to string
//    public String databaseToString(){
//        String dbString = "";
//        SQLiteDatabase db = getWritableDatabase();
//        String query = "SELECT " + COLUMN_ACTIVITY + " FROM " + TABLE_NAME + " WHERE 1"; //"SELECT * FROM "
//
//        //cursor point to a location in my results
//        Cursor c = db.rawQuery(query,null);
//        //move to the first row
//        c.moveToFirst();
//
//        while (!c.isAfterLast()){
//            if(c.getString(c.getColumnIndex("activity"))!=null){//activity statt productname
//                dbString += c.getString(c.getColumnIndex("activity"));
//                dbString += "\n";
//            }
//        }
//        c.moveToNext(); //aus yt comment
//        db.close();
//        return dbString;
//    }
//
//    public void moveToSD(){
//
//        SQLiteDatabase db = getWritableDatabase();
//
//        try {
//            // Backup of database to SDCard
//            //db.open();
//            File newFile = new File(Environment.getExternalStorageDirectory(), "saveSDcard"); //BackupFileNameHere
//            InputStream input = new FileInputStream("/data/data/com.example.atrt01admin.meinexample03/databases/save.db"); ///data/data/com.YourProjectName/databases/DatabaseNameHere
//            OutputStream output = new FileOutputStream(newFile);
//
//            // transfer bytes from the Input File to the Output File
//            byte[] buffer = new byte[1024];
//            int length;
//            while ((length = input.read(buffer))>0) {
//                output.write(buffer, 0, length);
//            }
//            output.flush();
//            output.close();
//            input.close();
//            db.close();
//
//        } catch (Exception e) {
//        }
//
//    }


    //From MainActivity
    //    private void printDatabase() {
//        String dbString = dbHandler.databaseToString();
//        System.out.println(dbString);
//        //sqliteTextView.setText("Database:");
//        //sqliteTextView.setText(dbString);
//        //mDetectedActivityTextView.setText(""); //nur wegen eingabeleiste löschen
//
//        //dbHandler.moveToSD();
//
//    }

    //printRecordItemList();
//                try {
//
//                    readFromFile();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                try {
//                    showDataTextView.setText(readFromFile());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }

    //printDatabase();
    //sqliteTextView.setText(String.valueOf(dbHandler.toString()));
    //SQLiteTextView(dbHandler.toString();
    //mLongitudeTextView.setText(String.valueOf(location.getLongitude()));

    //recordAddToList(activityToFile);
//                    try {
//                        writeToFile(activityToFile); //hier addToList
//
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
    //dbHandler.addRecord(getDetectedActivity(activity.getType()));

//    public void recordAddToList(String detectedActivity) {
//        String activity = detectedActivity;
//        Date dNow = new Date();
//        SimpleDateFormat ft = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
//        RecordItem recordItem = new RecordItem(activity, latitude, longitude, ft.format(dNow));
//        recordItemList.add(recordItem);
//    }
//
//    public void printRecordItemList() {
//        for (RecordItem recordItem : recordItemList) {
//            System.out.println(recordItem.recordItemToString());
//        }
//    }

    //    public void writeToFile(String detectedActivity) throws IOException {
//        String filename = "save.txt";   // fos - txt approach
//        String activity = detectedActivity;
//        String[] record = new String[3];
//        FileOutputStream fos;
//        try{
//            fos = openFileOutput(filename,MODE_APPEND);//MODE_APPEND
//            fos.write(activity.getBytes());
//            fos.close();
//
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        String activity = detectedActivity;
//        //get timestamp
//        Date dNow = new Date();
//        SimpleDateFormat ft = new SimpleDateFormat ("yyyy.MM.dd HH:mm:ss");
    //json approach http://www.java2blog.com/2013/11/gson-example-read-and-write-json.html
    //Gson gson = new GsonBuilder().serializeNulls().create();
//        Gson gson = new Gson();
//        RecordItem recordItem = new RecordItem(activity,latitude,longitude,ft.format(dNow));
//        String json = gson.toJson(recordItem);
//        String myFile = context.getFilesDir().getPath().toString() + "/" + "records.json";
//
//
//
//        FileOutputStream fOut = new FileOutputStream(myFile);
//        OutputStreamWriter myOutWriter =new OutputStreamWriter(fOut);
//        myOutWriter.append(json);
//        System.out.println(myFile);
//        myOutWriter.close();
//        fOut.close();
//        System.out.println(json.toString());



    //        JSONObject object = new JSONObject();
//        try {
//            object.put("activity", activity);
//            object.put("latitude", new Double(latitude));
//            object.put("longitude", new Double(longitude));
//            object.put("date", (String.valueOf(dNow)));
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        System.out.println(object);
//        object.length()

//        context = getApplicationContext();
//        Utils.loadData(context);
//        Utils.addData(klasse);
//        Utils.saveData(context);

    //http://stackoverflow.com/questions/19459082/read-and-write-data-with-gson
//        File myFile = new File("/sdcard/records.txt");
    //myFile.createNewFile();
//        FileOutputStream fOut = new FileOutputStream(myFile);
//        OutputStreamWriter myOutWriter =new OutputStreamWriter(fOut);
//        myOutWriter.append(json);
//        System.out.println(myFile);
//        myOutWriter.close();
//        fOut.close();

//        FileOutputStream fos;
//        //        FileOutputStream fos;
//        try{
//            fos = openFileOutput("records",MODE_PRIVATE);//MODE_APPEND
//            fos.write(json.getBytes());
//            fos.close();
//
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


//        try{
//            //write converted json to file
//            FileWriter writer = new FileWriter("records.json");
//            writer.write(json);
//            writer.close();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

//    }

//    public void readFromFile () throws IOException, JSONException {

//        String in=null;
//        JSONObject reader = new JSONObject(in);
//
//        JSONObject record = reader.getJSONObject("RecordItem");
//        RecordItem recordItem = new RecordItem();
//        recordItem.setActivity(record.getString("activity"));



//        StringBuffer datax = new StringBuffer("");
//        try {
//            FileInputStream fis = openFileInput ( "save.txt" ) ;
//            InputStreamReader isr = new InputStreamReader ( fis ) ;
//            BufferedReader buffreader = new BufferedReader ( isr ) ;
//
//            String readString = buffreader.readLine ( ) ;
//            while ( readString != null ) {
//                datax.append(readString);
//                readString = buffreader.readLine ( ) ;
//            }
//
//            isr.close ( ) ;
//        } catch ( IOException ioe ) {
//            ioe.printStackTrace ( ) ;
//        }
//        return datax.toString() ;

//        ArrayList<RecordItem> recordItemArrayList = new ArrayList<>();
//        Type listOfObject= new TypeToken<ArrayList<RecordItem>>(){}.getType();
//        String myFile = context.getFilesDir().getPath().toString() + "/" + "records.json";
//        System.out.println(myFile);
//        Gson gson = new Gson();
//        Reader reader;
//
//        reader = new InputStreamReader(new FileInputStream(myFile));
//        recordItemArrayList = gson.fromJson(reader,listOfObject);
//        reader.close();
//        RecordItem.setActivity(recordItemArrayList);
//
    //File myFile = new File("/sdcard/records.txt");
//        FileInputStream fIn = new FileInputStream(myFile);
//        BufferedReader myReader = new BufferedReader(new InputStreamReader(fIn));
//        String aDataRow = "";
//        String aBuffer = ""; //Holds the text
//        while ((aDataRow = myReader.readLine()) != null)
//        {
//            aBuffer += aDataRow ;
//        }
//        return aBuffer;
//        myReader.close();
//
//        Gson gson = new Gson();
//
//        String myFile = context.getFilesDir().getPath().toString() + "/" + "records.json";
//        BufferedReader br = new BufferedReader(
//                new FileReader(myFile));

    //for(int i=0;i<myFile.length();i++) {
    //convert the json string back to object
//            recordItemList = gson.fromJson(br, List.class);
//            RecordItem recordItem = gson.fromJson(br, RecordItem.class);
//            System.out.println("\n readFromFile:");
//            System.out.println(recordItem.getActivity());
    //return recordItem.toString();
    //}
//        Gson GsonObject = new Gson();
//
//        String JSInput = "[{\"value1\":\"one\",\"value2\":1},{\"value1\":\"two\",\"value2\":2},{\"value1\":\"three\",\"value2\":3}]";
//        TabClass[] Input_String = GsonObject.fromJson(JSInput, TabClass[].class);
//        System.out.println(Arrays.toString(Input_String));
//
//
//    }
//
//}

    /*
    public void gsonBuilder() {
        Gson gson = new GsonBuilder().serializeNulls().create();
        RecordItem recordItem = new RecordItem();
        String json = gson.toJson(recordItem);
        System.out.println(json);

        json = gson.toJson(null);
        System.out.println(json);
    }
*/

//
//String filename = "myfile";
//String string = "Hello world!";
//FileOutputStream outputStream;
//
//try {
//        outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
//        outputStream.write(string.getBytes());
//        outputStream.close();
//        } catch (Exception e) {
//        e.printStackTrace();
//        }

//String fileName = context.getFilesDir().getPath().toString() + "virtual_companion_blutdrucks1.json";
//context.getFilesDir().getPath()

//    openFileOutput("savedData.txt", MODE_APPEND | MODE_WORLD_READABLE );

//    // write text to file
//    public void WriteBtn(View v) {
//        // add-write text into file
//        try {
//            FileOutputStream fileout=openFileOutput("mytextfile.txt", MODE_PRIVATE);
//            OutputStreamWriter outputWriter=new OutputStreamWriter(fileout);
//            outputWriter.write(textmsg.getText().toString());
//            outputWriter.close();
//
//            //display file saved message
//            Toast.makeText(getBaseContext(), "File saved successfully!",
//                    Toast.LENGTH_SHORT).show();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

//String fileName = context.getFilesDir().getPath().toString() + "virtual_companion_blutdrucks1.json";
//context.getFilesDir().getPath()

//context = getApplicationContext();
//        Utils.loadData(context);




}