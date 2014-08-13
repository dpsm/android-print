android-print
============
An Android library to interact with the Google Cloud Print APIs. Google Play Services does not provide an APIs to interact with the print services. Have fun :)

Usage
---------

The sections below illustrate how to use the library APIs in more detail. The API is based on [rxjava][1].

All APIs depend on a valid OAUth access token in order to interact with the Google Cloud Print Services. In order to easily retrieve tokens you can use the [android-auth][2] library.

### GoogleCloudPrint

The entire library features are provided by the GoogleCloudPrint class. There are helper classes based on GSON and Jackson. Feel free to use whichever suits you better or none and interact with the responses directly.

```
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mGoogleCloudPrint = new GoogleCloudPrint();
    ...
}
```

> **Tip:** You can create one instance at the application scope and reuse across the application.

### Retrieving Printers
```
mUpdatePrinterListAction = new Action1<PrinterSearchResult>() {
    @Override
    public void call(final PrinterSearchResult response) {
        final List<Printer> printers = response.getPrinters();
        // Do something cool with the printers!
    }
};
    
...

mGoogleCloudPrint.getPrinters(token)
//  .lift(new JacksonPrinterSearchResultOperator()) <= Jackson fans!
//  .lift(new GsonPrinterSearchResultOperator()) <= GSON fans!
    .observeOn(AndroidSchedulers.mainThread())
    .subscribeOn(Schedulers.io())
    .subscribe(mUpdatePrinterListAction);
...
```

### Retrieving Printer Details
```
mUpdatePrinterListAction = new Action1<Printer>() {
    @Override
    public void call(final Printer response) {
        // Do something cool with the printer!
    }
};

mShowPrinterNotFoundAction = new Action1<Throwable>() {
    @Override
    public void call(final Throwable throwable) {
        // Something went wrong...
    }
};
 
...

mGoogleCloudPrint.getPrinter(token, selectedPrinter.getId(), false, null)
    .observeOn(AndroidSchedulers.mainThread())
    .subscribeOn(Schedulers.io())
    .subscribe(mShowResponseAction, mShowPrinterNotFoundAction);
...
```

### Retrieving Printer Jobs

You can also interact directly with the server responses such as in the example below.

```
mShowResponseAction = new Action1<Response>() {
    @Override
    public void call(final Response response) {
        if (response.getStatus() == 200) {
            // Retrieve Json from response.getBody()
        }
    }
};
...

mGoogleCloudPrint.getJobs(token, selectedPrinter.getId(), null, null)
    .observeOn(AndroidSchedulers.mainThread())
    .subscribeOn(Schedulers.io())
    .subscribe(mShowResponseAction);
...
```

### Submitting a Print Job

Submitting a print job is super easy, just use the target printer ID and the File to print!

```
final File file = ...

mGoogleCloudPrint.submitPrintJob(token, selectedPrinter.getId(), file.getName(), TICKET,
    new TypedFile("image/jpeg", file))
    .observeOn(AndroidSchedulers.mainThread())
    .subscribeOn(Schedulers.io())
    .subscribe(mShowResponseAction, new Action1<Throwable>() {
        @Override
        public void call(final Throwable throwable) {
            Toast.makeText(MainActivity.this,
                    "Print failed :(", Toast.LENGTH_SHORT).show();
        }
    });
...
```


  [1]: https://github.com/Netflix/RxJava
  [2]: https://github.com/dpsm/android-auth