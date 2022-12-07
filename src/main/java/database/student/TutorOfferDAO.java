package database.student;

import com.mongodb.client.*;
import database.utils.BUGUtils;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.*;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.pull;

public class TutorOfferDAO {
    private static MongoCursor<Document> cursor;

    void createTutorOffer(String id, String username, String courseCode, String professorTaken, String semesterTaken, Double hourlyRate){
        TutorOffer t = new TutorOffer(id, username, courseCode, professorTaken, semesterTaken, hourlyRate);
        MongoCollection<Document> collection = BUGUtils.database.getCollection("tutorOffers");
        Bson filter = eq("_id", id);

        Document d = toDocument(t);
        if(fetchTutorOfferInfo(t.getId()) == null) {
            collection.insertOne(d);
        } else {
            collection.deleteOne(filter);
            collection.insertOne(d);
        }
    }

    TutorOffer fetchTutorOfferInfo(String id){
        MongoCollection<Document> collection = BUGUtils.database.getCollection("tutorOffers");
        Bson filter = eq("_id", id);
        cursor = collection.find(filter).iterator();
        if (cursor.hasNext())
            return toTutorOffer(cursor.next());
        else return null;
    }

    public static Document toDocument(TutorOffer offer) {
        return new Document("_id", offer.getId())
                .append("username", offer.getUsername())
                .append("courseCode", offer.getCourseCode())
                .append("professorTaken", offer.getProfessorTaken())
                .append("semesterTaken", offer.getSemesterTaken())
                .append("hourlyRate", offer.getHourlyRate());
    }

    static TutorOffer toTutorOffer(Document document) {
        return new TutorOffer(document.getString("_id"),
                document.getString("username"),
                document.getString("courseCode"),
                document.getString("professorTaken"),
                document.getString("semesterTaken"),
                document.getDouble("hourlyRate"));
    }

    // find all the tutor offers with the course code
    //
    static Vector<ArrayList<String>> getTutorOffers(String courseCode){
        Vector<ArrayList<String>> tutorOffers = new Vector<>();
        // querying TutorOffers collection
        MongoCollection<Document> tutorOffersCollection = BUGUtils.database.getCollection("tutorOffers");
        Bson filter = eq("courseCode", courseCode);
        cursor = tutorOffersCollection.find(filter).iterator();
        if (cursor.hasNext()){
            TutorOffer t = toTutorOffer(cursor.next());
            ArrayList<String> tutorOfferInfo = new ArrayList<>();

            tutorOfferInfo.add(t.getUsername());
            tutorOfferInfo.add(t.getCourseCode());
            tutorOfferInfo.add(t.getProfessorTaken());
            tutorOfferInfo.add(t.getSemesterTaken());
            tutorOfferInfo.add(t.getHourlyRate().toString());

            tutorOffers.add(tutorOfferInfo);
        }

        return tutorOffers;
    }

    String getTutorCourse(String tutorId){
        MongoCollection<Document> collection = BUGUtils.database.getCollection("tutorOffers");
        Bson filter = eq("_id", tutorId);
        Document d = collection.find(filter).first();
        return d.getString("courseCode");
    }

    void removeOffer(String username, String courseCode){
        MongoCollection<Document> tutorOfferCollection = BUGUtils.database.getCollection("tutorOffers");
        Bson filter = and(eq("courseCode", courseCode), eq("username", username));
        tutorOfferCollection.deleteOne(filter);
    }
}
