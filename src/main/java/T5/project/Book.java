package T5.project;

public abstract class Book {
   private String bookTitle;
   private String bookId;
   private boolean isAvailable;

   public Book(String bookTitle, String bookId ){
    setBookTitle(bookTitle);
    setBookId(bookId);
    this.isAvailable = isAvailable;
   }

   public void setBookTitle(String bookTitle){
    If(bookId == null || bookId.isEmpty()){
        throw new IllegalArgumentException("Book title can not empty");
    }
    this.bookTitle = bookTitle;
   }
   public String getBookId(){
    return bookId;
   }
   public void setBookId(String bookId){
    If(bookId == null || bookId.isEmpty()){
        throw new IllegalArgumentException("Book ID can not empty");
    }
    this.bookId = bookId;
   } 
   public void setIsAvailable(boolean isAvailable){
        this.isAvailable = isAvailable;
    }
    public boolean getIsAvailable(){
     return isAvailable;
    }
}
