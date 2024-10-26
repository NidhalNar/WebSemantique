package tn.sem.websem;

import java.io.OutputStream;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDF;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class RestApi {

    Model model = JenaEngine.readModel("data/rescuefood.owl");

    @GetMapping("/inventory")
    @CrossOrigin(origins = "http://localhost:4200")
    public String afficherInventory() {
        String NS = "";
        if (model != null) {
            NS = model.getNsPrefixURI("");

            Model inferedModel = JenaEngine.readInferencedModelFromRuleFile(model, "data/rules.txt");

            OutputStream res = JenaEngine.executeQueryFile(inferedModel, "data/query_Inventory.txt");

            System.out.println(res);
            return res.toString();

        } else {
            return ("Error when reading model from ontology");
        }
    }

    @GetMapping("/restaurant")
    @CrossOrigin(origins = "http://localhost:4200")
    public String afficherRestaurant() {
        String NS = "";
        if (model != null) {
            NS = model.getNsPrefixURI("");

            Model inferedModel = JenaEngine.readInferencedModelFromRuleFile(model, "data/rules.txt");

            OutputStream res = JenaEngine.executeQueryFile(inferedModel, "data/query_Restaurant.txt");

            System.out.println(res);
            return res.toString();

        } else {
            return ("Error when reading model from ontology");
        }
    }
    @PostMapping("/addRestaurant")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<String> addRestaurant(@RequestBody RestaurantDto restaurantDto) {
        if (model != null) {
            try {
                Resource restaurantResource = model.createResource(restaurantDto.getRestaurant());

                Property nameProperty = model.createProperty("http://rescuefood.org/ontology#name");
                Property contactProperty = model.createProperty("http://rescuefood.org/ontology#contact");
                Property addressProperty = model.createProperty("http://rescuefood.org/ontology#address");

                model.add(restaurantResource, RDF.type, model.createResource("http://rescuefood.org/ontology#Restaurant"));
                model.add(restaurantResource, nameProperty, restaurantDto.getName());
                model.add(restaurantResource, contactProperty, restaurantDto.getContact());
                model.add(restaurantResource, addressProperty, restaurantDto.getAddress());

                JenaEngine.saveModel(model, "data/rescuefood.owl");

                return new ResponseEntity<>("Restaurant added successfully", HttpStatus.CREATED);
            } catch (Exception e) {
                return new ResponseEntity<>("Error adding restaurant: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>("Error when reading model from ontology", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/modifyRestaurant")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<String> modifyRestaurant(@RequestBody RestaurantDto restaurantDto) {
        if (model != null) {
            try {
                Resource restaurantResource = model.getResource(restaurantDto.getRestaurant());
                if (restaurantResource == null) {
                    return new ResponseEntity<>("Restaurant not found", HttpStatus.NOT_FOUND);
                }

                Property nameProperty = model.createProperty("http://rescuefood.org/ontology#name");
                Property contactProperty = model.createProperty("http://rescuefood.org/ontology#contact");
                Property addressProperty = model.createProperty("http://rescuefood.org/ontology#address");

                model.removeAll(restaurantResource, nameProperty, null);
                model.removeAll(restaurantResource, contactProperty, null);
                model.removeAll(restaurantResource, addressProperty, null);

                model.add(restaurantResource, nameProperty, restaurantDto.getName());
                model.add(restaurantResource, contactProperty, restaurantDto.getContact());
                model.add(restaurantResource, addressProperty, restaurantDto.getAddress());

                JenaEngine.saveModel(model, "data/rescuefood.owl");

                return new ResponseEntity<>("Restaurant modified successfully", HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>("Error modifying restaurant: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>("Error when reading model from ontology", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/deleteRestaurant")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<String> deleteRestaurant(@RequestBody RestaurantDto restaurantDto) {
        String restaurantUri = restaurantDto.getRestaurant();

        System.out.println("Received request to delete restaurant: " + restaurantUri);

        if (model != null) {
            try {
                Resource restaurantResource = model.getResource(restaurantUri);
                if (restaurantResource == null) {
                    return new ResponseEntity<>("Restaurant not found", HttpStatus.NOT_FOUND);
                }

                model.removeAll(restaurantResource, null, null);

                JenaEngine.saveModel(model, "data/rescuefood.owl");

                return new ResponseEntity<>("Restaurant deleted successfully", HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>("Error deleting restaurant: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>("Error when reading model from ontology", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }







    @GetMapping("/food")
    @CrossOrigin(origins = "http://localhost:4200")
    public String afficherFood() {
        String NS = "";
        if (model != null) {
            NS = model.getNsPrefixURI("");

            Model inferedModel = JenaEngine.readInferencedModelFromRuleFile(model, "data/rules.txt");

            OutputStream res = JenaEngine.executeQueryFile(inferedModel, "data/query_Food.txt");

            System.out.println(res);
            return res.toString();

        } else {
            return ("Error when reading model from ontology");
        }
    }

    @GetMapping("/manager")
    @CrossOrigin(origins = "http://localhost:4200")
    public String afficherManager() {
        String NS = "";
        if (model != null) {
            NS = model.getNsPrefixURI("");

            Model inferedModel = JenaEngine.readInferencedModelFromRuleFile(model, "data/rules.txt");

            OutputStream res = JenaEngine.executeQueryFile(inferedModel, "data/query_Manager.txt");

            System.out.println(res);
            return res.toString();

        } else {
            return ("Error when reading model from ontology");
        }
    }


    @GetMapping("/notification")
    @CrossOrigin(origins = "http://localhost:4200")
    public String afficherNotification() {
        String NS = "";
        if (model != null) {
            NS = model.getNsPrefixURI("");

            Model inferedModel = JenaEngine.readInferencedModelFromRuleFile(model, "data/rules.txt");

            OutputStream res = JenaEngine.executeQueryFile(inferedModel, "data/query_Notification.txt");

            System.out.println(res);
            return res.toString();

        } else {
            return ("Error when reading model from ontology");
        }
    }

    @GetMapping("/director")
    @CrossOrigin(origins = "http://localhost:4200")
    public String afficherDirector() {
        String NS = "";
        if (model != null) {
            NS = model.getNsPrefixURI("");
            Model inferredModel = JenaEngine.readInferencedModelFromRuleFile(model, "data/rules.txt");
            OutputStream res = JenaEngine.executeQueryFile(inferredModel, "data/query_Director.txt");
            System.out.println(res);
            return res.toString();
        } else {
            return ("Error when reading model from ontology");
        }
    }

    @GetMapping("/collectionevent")
    @CrossOrigin(origins = "http://localhost:4200")
    public String afficherCollectionEvent() {
        String NS = "";
        if (model != null) {
            NS = model.getNsPrefixURI("");
            Model inferredModel = JenaEngine.readInferencedModelFromRuleFile(model, "data/rules.txt");
            OutputStream res = JenaEngine.executeQueryFile(inferredModel, "data/query_CollectionEvent.txt");
            System.out.println(res);
            return res.toString();
        } else {
            return ("Error when reading model from ontology");
        }
    }

    @GetMapping("/request")
    @CrossOrigin(origins = "http://localhost:4200")
    public String afficherRequest() {
        String NS = "";
        if (model != null) {
            NS = model.getNsPrefixURI("");
            Model inferredModel = JenaEngine.readInferencedModelFromRuleFile(model, "data/rules.txt");
            OutputStream res = JenaEngine.executeQueryFile(inferredModel, "data/query_Request.txt");
            System.out.println(res);
            return res.toString();
        } else {
            return ("Error when reading model from ontology");
        }

    }
    @GetMapping("/feedback")
    @CrossOrigin(origins = "http://localhost:4200")
    public String afficherFeedback() {
        String NS = "";
        if (model != null) {
            NS = model.getNsPrefixURI("");
            Model inferredModel = JenaEngine.readInferencedModelFromRuleFile(model, "data/rules.txt");
            OutputStream res = JenaEngine.executeQueryFile(inferredModel, "data/query_Feedback.txt");
            System.out.println(res);
            return res.toString();
        } else {
            return ("Error when reading model from ontology");
        }
    }
    @PostMapping("/addFeedback")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<String> addFeedback(@RequestBody FeedbackDto feedbackDto) {
        if (model != null) {
            try {
                Resource feedbackResource = model.createResource(feedbackDto.getFeedback());

                Property ratingProperty = model.createProperty("http://rescuefood.org/ontology#rating");
                Property commentProperty = model.createProperty("http://rescuefood.org/ontology#comment");

                model.add(feedbackResource, RDF.type, model.createResource("http://rescuefood.org/ontology#Feedback"));
                model.add(feedbackResource, ratingProperty, feedbackDto.getRating().toString());
                model.add(feedbackResource, commentProperty, feedbackDto.getComment());

                JenaEngine.saveModel(model, "data/rescuefood.owl");

                return new ResponseEntity<>("Feedback ajouté avec succès", HttpStatus.CREATED);
            } catch (Exception e) {
                return new ResponseEntity<>("Erreur lors de l'ajout du feedback : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>("Erreur lors de la lecture du modèle de l'ontologie", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/modifyFeedback")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<String> modifyFeedback(@RequestBody FeedbackDto feedbackDto) {
        if (model != null) {
            try {
                Resource feedbackResource = model.getResource(feedbackDto.getFeedback());
                if (feedbackResource == null) {
                    return new ResponseEntity<>("Feedback not found", HttpStatus.NOT_FOUND);
                }

                Property ratingProperty = model.createProperty("http://rescuefood.org/ontology#rating");
                Property commentProperty = model.createProperty("http://rescuefood.org/ontology#comment");

                model.removeAll(feedbackResource, ratingProperty, null);
                model.removeAll(feedbackResource, commentProperty, null);

                model.add(feedbackResource, ratingProperty, feedbackDto.getRating().toString());
                model.add(feedbackResource, commentProperty, feedbackDto.getComment());

                JenaEngine.saveModel(model, "data/rescuefood.owl");

                return new ResponseEntity<>("Feedback modified successfully", HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>("Error modifying feedback: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>("Error when reading model from ontology", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/deleteFeedback")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<String> deleteFeedback(@RequestBody FeedbackDto feedbackDto) {
        String feedbackUri = feedbackDto.getFeedback();

        System.out.println("Received request to delete feedback: " + feedbackUri);

        if (model != null) {
            try {
                Resource feedbackResource = model.getResource(feedbackUri);
                if (feedbackResource == null) {
                    return new ResponseEntity<>("Feedback not found", HttpStatus.NOT_FOUND);
                }

                model.removeAll(feedbackResource, null, null);

                JenaEngine.saveModel(model, "data/rescuefood.owl");

                return new ResponseEntity<>("Feedback deleted successfully", HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>("Error deleting feedback: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>("Error when reading model from ontology", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}
