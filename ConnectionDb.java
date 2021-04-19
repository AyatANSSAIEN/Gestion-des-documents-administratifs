
import java.sql.*;
import java.util.Calendar;

public class ConnectionDb {
   public int IntTest;

   // establish conn to database
   public Connection ConnectToDb(String dbName, String userName, String pass) throws Exception {
      Connection conn = null;
      Class.forName("com.mysql.cj.jdbc.Driver");
      conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + dbName, userName, pass);
      return conn;
   }

   // check if student exist
   public boolean CheckStudent(String CodeApoge) throws Exception {

      boolean isNumber = true;

      try {
         IntTest = Integer.parseInt(CodeApoge);
      } catch (NumberFormatException ex) {
         isNumber = false;
      }
      if (isNumber == true) {

         Connection cnt = this.ConnectToDb("glproject", "root", "");
         Statement stt = cnt.createStatement();
         ResultSet rs = stt.executeQuery("SELECT * FROM etudiants WHERE CodeApoge=" + CodeApoge);
         if (rs.next())
            return true;
      }

      return false ;
   }

   // save the student demande
   public boolean saveDemande(String CodeApoge, String Email, String document) throws Exception {
      if (new Email().isEmail(Email)) {

         boolean isNumber = true;
      
         try {
            IntTest = Integer.parseInt(CodeApoge);
         } catch (NumberFormatException ex) {
            isNumber = false;
         }
         if (isNumber == true) {

            Connection cnt = this.ConnectToDb("glproject", "root", "");
            // test if the student demande same file
            Calendar calendar = Calendar.getInstance();
            Date ourJavaDateObject = new Date(calendar.getTime().getTime());
           
            String str = ourJavaDateObject.toString();
            int year = Integer.parseInt(str.substring(0, 4)) - 1;
            String date = year + "" + str.substring(4); 

            Statement stt = cnt.createStatement();
            String str0 = "SELECT * FROM demandes WHERE CodeApogee=" + CodeApoge + " AND Type_document=\"" + document
                  + "\" AND date_demande >\"" + date + "\" AND date_demande <= \"" + ourJavaDateObject + "\"";
            ResultSet rs = stt.executeQuery(str0);

            if (!rs.next()) {
               Statement insertStt = cnt.createStatement();
               insertStt.executeUpdate(
                     "INSERT INTO `demandes`(`date_demande`, `Type_document`, `Statut`, `CodeApogee`) VALUES (\""
                           + ourJavaDateObject + "\",\"" + document + "\",\"att\"," + CodeApoge + ")");
               insertStt
                     .executeUpdate("UPDATE `etudiants` SET `Email`=\"" + Email + "\" WHERE CodeApoge = " + CodeApoge);

               return true;
            }
         }
      }
      return false;
   }
   //delete * from demandes

   public String getEmail(Object id_demande) throws Exception {
      Connection cnt = this.ConnectToDb("glproject", "root", "");
      Statement stt = cnt.createStatement();
      String rqst = "SELECT Email FROM `demandes` INNER JOIN `etudiants` ON demandes.CodeApogee = etudiants.CodeApoge WHERE id_demande ="
            + id_demande.toString();
      ResultSet rs = stt.executeQuery(rqst);
      if (rs.next())
         return rs.getString(1);
      else
         return null;
   }

   public ResultSet getStudent(Object id) throws Exception {
      Connection cnt = this.ConnectToDb("glproject", "root", "");
      Statement stt = cnt.createStatement();
      ResultSet rs = stt.executeQuery(
            "select * from etudiants inner join demandes where etudiants.CodeApoge=demandes.CodeApogee AND id_demande="
                  + id.toString());
      return rs;
   }

   // check if the admin information are correct
   public boolean isAdmin(String username, String pass) throws Exception {
      Connection cnt = this.ConnectToDb("glproject", "root", "");
      Statement stt = cnt.createStatement();
      String rqst = "SELECT * FROM `admins` WHERE `login`=\"" + username + "\" AND `password`=\"" + pass + "\"";
      ResultSet rs = stt.executeQuery(rqst);
      if (rs.next())
         return true;
      else
         return false;
   }

   // return list of demandes
   public ResultSet ShowDemandes() throws Exception {
      Connection cnt = this.ConnectToDb("glproject", "root", "");
      Statement stt = cnt.createStatement();

      ResultSet rs = stt.executeQuery(
            "select id_demande,Type_document,CodeApoge,nom,prenom from demandes inner join etudiants on demandes.CodeApogee = etudiants.CodeApoge where statut=\"att\" order by date_demande");
      return rs;
      // chercher le `nom`, `prenom` From eutdiant where CodeApoge = ...
   }

   // return list of demandes
   public ResultSet ShowHisto() throws Exception {
      Connection cnt = this.ConnectToDb("glproject", "root", "");
      Statement stt = cnt.createStatement();

      ResultSet rs = stt.executeQuery(
            "select Type_document,CodeApoge,nom,prenom,statut,date_demande from demandes inner join etudiants on demandes.CodeApogee = etudiants.CodeApoge where statut != 'att' order by date_demande");
      return rs;
      // chercher le `nom`, `prenom` From eutdiant where CodeApoge = ...
   }
   // valide deamnde
   public void ValideDemand(Object demandeId) throws Exception {
      Connection cnt = this.ConnectToDb("glproject", "root", "");
      Statement stt = cnt.createStatement();
      String rqst = "UPDATE demandes SET statut=\"Acceptée\" WHERE id_demande=" + demandeId.toString();
      stt.executeUpdate(rqst);
      // Send Mail () ;
   }

   // reject demande
   public void RejeteDemand(Object demandeId) throws Exception {
      Connection cnt = this.ConnectToDb("glproject", "root", "");
      Statement stt = cnt.createStatement();
      stt.execute("UPDATE demandes SET statut=\"Rejetée\" WHERE id_demande=" + demandeId.toString());
   }
   // delete * from demandes
   public void DeleteDemandes() throws Exception {
      Connection cnt = this.ConnectToDb("glproject", "root", "");
      Statement stt = cnt.createStatement();
      stt.executeUpdate("DELETE FROM `demandes` WHERE Statut != 'att'");
   }

   public String GetDocType(Object demandeId) throws Exception {
      Connection cnt = this.ConnectToDb("glproject", "root", "");
      Statement stt = cnt.createStatement();
      ResultSet rs = stt.executeQuery("SELECT `Type_document` FROM `demandes` WHERE `id_demande` = " + demandeId);
      if (rs.next())
         return rs.getString(1);
      return null;
   }
}