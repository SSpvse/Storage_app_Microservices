Instructions for project using postman:


1.1 To start off, register a user.

  POST http://localhost:3000/users/register
  {
  "username":" ",
  "email": " ",
  "password": ""
  }

1.2

  Login with the registered user.

  POST http://localhost:8000/auth/login
  {
  "email": " ",
  "password": ""
  }
  
  This here should give back a ownerID in the context.
  
-- This has to do with handling units: 



1.3
        GET http://localhost:8000/auth/getMail-by-id/{userID}

        this will give back the email of the user with the ID 3.


1.4

        GET http://localhost:8000/unit/units-for-guest/1

        This will give back all the units that the user with the ID 1 is a guest in.
        OR 
        It will give back units that hes granted OWNER role to aswell.

                        UNIT

2.1
    
    Create a unit.

   POST http://localhost:8000/unit/addunit

   {
   "name":"Fridge",
   "description":"white fridge",
   "location":"Kitchen",
   "type": "Fridge",
   "ownerId":4
   }

2.2

    GET http://localhost:8000/unit/byowner/3

    This should return all units owned by the owner with the ID 3.

2.3 

    DELETE http://localhost:8000/unit/delete/1
    
    This should delete the unit with the ID 1.

2.4

    POST http://localhost:8000/unit/invite

    This is to send an invite to another registered user , and give him 
    access to the units you choose.

    The body should have the units ID and the owners ID that react sends from its context assosiated with the user
    that is logged in. Then User will add email and role of OWNER or GUEST.

    {
    "unitID":"10",
    "ownerID":"2",
    "guestEmail": "1@m",
    "role":"OWNER"
    }

    After the invitation , the User with that email will be able to edit and delete if he has role of OWNER.

2.5

    DELETE http://localhost:8000/unit/delete/{unitID} 

    (this is a quick fix that we had to use , because of issues with time)
    The method that is commented out was supposed to take in UnitDTO and delete according to permission.

2.6

    POST http://localhost:8000/unit/update/{ownedrID}

    This is to update the unit with the ID that is given in the path.

    {
    "name":"Fridge",
    "description":"white fridge",
    "location":"Kitchen",
    "type": "Fridge",
    "ownerId":4
    }

2.7

    GET http://localhost:8000/unit/byowner/{ownerID}

    This should return all units that are connected to the ownerID.


                 --   ITEM   -- 

3.1

    POST http://localhost:8000/item/additem
    
        This is to add an item to a unit, if unit doesent exist it wont be able to make item.
        If the Item has date on it like this one bellow, it will create DateDTO and send it to NotificationService
        where it will be stored and monitored for expiration. Day before the date given it will send to RabbitMQ and 
        then the EmailService will pick it up and send an email to the user.

        {
            "name":"Milk",
            "description":"fat milk",
            "quantity": 1,
            "date":"2024-12-12", 
            "type": "Milk",
            "unitID": 1,
            "userID": 4,
            "type":"food"
        }

3.2

    GET http://localhost:8000/item/byid/{unitID}

    This will get all items accociated with the unitID.
    
3.3

    POST http://localhost:8000/item/update/{itemID}

    update item with the item object, all fields that are not null will replace the old ones.
    

     {
    
        "name": "Milk",
        "description": " skin milk ",
        "quantity": 3,
        "date": "2024-12-12",
        "unitID": 1,
        "userID": 4
    }


3.4

    DELETE http://localhost:8000/item/delete/{itemID}

    This will delete the item with the itemID.



