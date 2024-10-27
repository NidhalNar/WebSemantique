package tn.sem.websem;

import java.io.OutputStream;
import java.util.UUID;

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
                // Generate a random URL for the restaurant resource using UUID
                String restaurantResourceUri = "http://rescuefood.org/ontology#Restaurant" + UUID.randomUUID().toString();

                // Create the resource with the generated URL
                Resource restaurantResource = model.createResource(restaurantResourceUri);

                Property nameProperty = model.createProperty("http://rescuefood.org/ontology#name");
                Property contactProperty = model.createProperty("http://rescuefood.org/ontology#contact");
                Property addressProperty = model.createProperty("http://rescuefood.org/ontology#address");

                // Add RDF type for the resource
                model.add(restaurantResource, RDF.type, model.createResource("http://rescuefood.org/ontology#Restaurant"));

                // Add properties to the resource
                model.add(restaurantResource, nameProperty, restaurantDto.getName());
                model.add(restaurantResource, contactProperty, restaurantDto.getContact());
                model.add(restaurantResource, addressProperty, restaurantDto.getAddress());

                // Save the model
                JenaEngine.saveModel(model, "data/rescuefood.owl");

                return new ResponseEntity<>("Restaurant added successfully: " + restaurantResourceUri, HttpStatus.CREATED);
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
    @PostMapping("/addFood")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<String> addFood2(@RequestBody FoodDto foodDto) {
        if (model != null) {
            try {
                // Generate a random URL for the food resource using UUID
                String foodResourceUri = "http://rescuefood.org/ontology#Food" + UUID.randomUUID().toString();

                // Create the resource with the generated URL
                Resource restaurantResource = model.createResource(foodResourceUri);

                Property foodTypeProperty = model.createProperty("http://rescuefood.org/ontology#foodType");
                Property quantityProperty = model.createProperty("http://rescuefood.org/ontology#quantity");
                Property expiryDateProperty = model.createProperty("http://rescuefood.org/ontology#expiryDate");

                // Add RDF type for the resource
                model.add(restaurantResource, RDF.type, model.createResource("http://rescuefood.org/ontology#Food"));

                // Add properties to the resource
                model.add(restaurantResource, foodTypeProperty, foodDto.getFoodType());
                model.add(restaurantResource, quantityProperty, foodDto.getQuantity());
                model.add(restaurantResource, expiryDateProperty, foodDto.getExpiryDate());

                // Save the model
                JenaEngine.saveModel(model, "data/rescuefood.owl");

                return new ResponseEntity<>("Food added successfully: " + foodResourceUri, HttpStatus.CREATED);
            } catch (Exception e) {
                return new ResponseEntity<>("Error adding Food: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>("Error when reading model from ontology", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/updateFood")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<String> updateFood(@RequestBody FoodDto foodDto) {
        if (model != null) {
            try {
                // Create the resource URI from the FoodDto
                String foodResourceUri = foodDto.getFood(); // Assuming the URI is passed in the foodDto

                // Check if the resource exists
                Resource restaurantResource = model.getResource(foodResourceUri);
                if (restaurantResource == null) {
                    return new ResponseEntity<>("Food resource not found", HttpStatus.NOT_FOUND);
                }

                Property foodTypeProperty = model.createProperty("http://rescuefood.org/ontology#foodType");
                Property quantityProperty = model.createProperty("http://rescuefood.org/ontology#quantity");
                Property expiryDateProperty = model.createProperty("http://rescuefood.org/ontology#expiryDate");

                // Update properties of the existing resource
                model.removeAll(restaurantResource, foodTypeProperty, null); // Remove existing foodType
                model.add(restaurantResource, foodTypeProperty, foodDto.getFoodType()); // Add updated foodType

                model.removeAll(restaurantResource, quantityProperty, null); // Remove existing quantity
                model.add(restaurantResource, quantityProperty, foodDto.getQuantity()); // Add updated quantity

                model.removeAll(restaurantResource, expiryDateProperty, null); // Remove existing expiryDate
                model.add(restaurantResource, expiryDateProperty, foodDto.getExpiryDate()); // Add updated expiryDate

                // Save the updated model
                JenaEngine.saveModel(model, "data/rescuefood.owl");

                return new ResponseEntity<>("Food updated successfully: " + foodResourceUri, HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>("Error updating Food: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>("Error when reading model from ontology", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deleteFood")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<String> deleteFood(@RequestBody FoodDto foodDto) {
        if (model != null) {
            try {
                // Create the resource URI from the FoodDto
                String foodResourceUri = foodDto.getFood(); // Assuming the URI is passed in the foodDto

                // Check if the resource exists
                Resource restaurantResource = model.getResource(foodResourceUri);
                if (restaurantResource == null) {
                    return new ResponseEntity<>("Food resource not found", HttpStatus.NOT_FOUND);
                }

                // Remove the resource from the model
                model.removeAll(restaurantResource, null, null); // Remove all triples associated with this resource
                model.remove(restaurantResource, RDF.type, model.createResource("http://rescuefood.org/ontology#Food")); // Specifically remove the RDF type

                // Save the updated model
                JenaEngine.saveModel(model, "data/rescuefood.owl");

                return new ResponseEntity<>("Food deleted successfully: " + foodResourceUri, HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>("Error deleting Food: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>("Error when reading model from ontology", HttpStatus.INTERNAL_SERVER_ERROR);
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
    @PostMapping("/addNotification")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<String> addNotification(@RequestBody NotificationDto notificationDto) {
        if (model != null) {
            try {
                Resource notificationResource = model.createResource(notificationDto.getNotification());

                Property recipientProperty = model.createProperty("http://rescuefood.org/ontology#recipient");
                Property notificationTypeProperty = model.createProperty("http://rescuefood.org/ontology#notificationType");

                model.add(notificationResource, RDF.type, model.createResource("http://rescuefood.org/ontology#Notification"));
                model.add(notificationResource, recipientProperty, notificationDto.getRecipient());
                model.add(notificationResource, notificationTypeProperty, notificationDto.getNotificationType());

                JenaEngine.saveModel(model, "data/rescuefood.owl");

                return new ResponseEntity<>("Notification ajoutée avec succès", HttpStatus.CREATED);
            } catch (Exception e) {
                return new ResponseEntity<>("Erreur lors de l'ajout de la notification : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>("Erreur lors de la lecture du modèle de l'ontologie", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/modifyNotification")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<String> modifyNotification(@RequestBody NotificationDto notificationDto) {
        if (model != null) {
            try {
                Resource notificationResource = model.getResource(notificationDto.getNotification());
                if (notificationResource == null) {
                    return new ResponseEntity<>("Notification not found", HttpStatus.NOT_FOUND);
                }

                Property recipientProperty = model.createProperty("http://rescuefood.org/ontology#recipient");
                Property notificationTypeProperty = model.createProperty("http://rescuefood.org/ontology#notificationType");

                model.removeAll(notificationResource, recipientProperty, null);
                model.removeAll(notificationResource, notificationTypeProperty, null);

                model.add(notificationResource, recipientProperty, notificationDto.getRecipient());
                model.add(notificationResource, notificationTypeProperty, notificationDto.getNotificationType());

                JenaEngine.saveModel(model, "data/rescuefood.owl");

                return new ResponseEntity<>("Notification modified successfully", HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>("Error modifying notification: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>("Error when reading model from ontology", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/deleteNotification")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<String> deleteNotification(@RequestBody NotificationDto notificationDto) {
        String notificationUri = notificationDto.getNotification();

        System.out.println("Received request to delete notification: " + notificationUri);

        if (model != null) {
            try {
                Resource notificationResource = model.getResource(notificationUri);
                if (notificationResource == null) {
                    return new ResponseEntity<>("Notification not found", HttpStatus.NOT_FOUND);
                }

                model.removeAll(notificationResource, null, null);

                JenaEngine.saveModel(model, "data/rescuefood.owl");

                return new ResponseEntity<>("Notification deleted successfully", HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>("Error deleting notification: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>("Error when reading model from ontology", HttpStatus.INTERNAL_SERVER_ERROR);
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
