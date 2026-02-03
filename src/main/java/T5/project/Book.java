package T5.project;

public class Book {
   private String bookTitle;
   private String bookId;
   private boolean isAvailable;

   public Book(String bookTitle, String bookId, boolean isAvailable){
    setBookTitle(bookTitle);
    setBookId(bookId);
    setIsAvailable(isAvailable);
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
