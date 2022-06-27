# Base App with Marvel App example 

Welcome to the android app developed in **Kotlin**. This app uses as a base a previous development that forms the basis of it.
Its simple operation is described below:

## Functioning

Our app will take the data from the web 
>"https://gateway.marvel.com/".

This website allows us to register as a user and use the api for free up to 3000 calls per day.
To make use of the requests it is necessary with our registration in it to obtain the private and public keys, necessary for each request.

### Screens
The app shows a list of characters from the **Marvel universe**, which we can select to see their image a little larger and, if it has one, a brief description.

|        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;        |<a href="url"><img src="https://github.com/magijon/BaseApp/blob/master/select_one_character.gif" align="right" height="400" width="200" ></a>                           |                         |
|----------------|-------------------------------|-----------------------------|



The app obtains a total of  **20** records from the api, but we can add 20 more in each search and store them within it. To do this, simply go down to the last record in the list, pull a little more and release the list, it will notify us with a  **loading** that the list is increasing.
|        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;        |<a href="url"><img src="https://github.com/magijon/BaseApp/blob/master/load_more_characters.gif" align="right" height="400" width="200" ></a>                           |                         |
|----------------|-------------------------------|-----------------------------|


Another of the tools that the app has is the possibility of **searching for a specific character** by the content of its name. To do this we will use the search bar that is hidden by default. In order to view it, we navigate through our list to the first record and pull down; We will then see how a space appears to be able to write with two buttons. If we write a name and click on the button with the drawing of a  **magnifying glass**, the search will be carried out, filtering the list with the records that contain said text (being in filter mode we will not be able to update the list with more characters). The other button with a  **x symbol** allows us to quickly erase the name that we have written and recover the complete list.
Finally, to hide the search bar, just navigate down it.

|        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;        |<a href="url"><img src="https://github.com/magijon/BaseApp/blob/master/use_search_bar.gif" align="right" height="400" width="200" ></a>                           |                         |
|----------------|-------------------------------|-----------------------------|


We can also remove characters from our list so that they are not loaded again. To do this, on the character that we want to delete, we drag to the left so that the **remove** button is shown, which will do the function of definitively eliminating the record from our list. We can hide the button by dragging to the opposite side.

|        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;        |<a href="url"><img src="https://github.com/magijon/BaseApp/blob/master/remove_one_character.gif" align="right" height="400" width="200" ></a>                           |                         |
|----------------|-------------------------------|-----------------------------|



## Technical details

For the development of the app, a **clean architecture** has been used, structuring said app in different modules with different scopes of vision.

The different modules are:
1. app
2. data
3. domain
4. usecase

In this way we will keep the different functions decoupled.
The design pattern used is MVVM mixed with the MVI to control the different states:
1. Loading
2. Success
3. Error

For asynchrony, the use of coroutines has been chosen.
To maintain fluid communication between the request and the emission, **Flow** has been used as a tool within the coroutines, so we do not close the flow, being able to issue different responses to the view, such as the status loading, error or success.

### App

This module comprises the presentation layer and contains the different views, which in this case are two: **ListViewFragment** and **CharacterFragment**, in addition to the container activity where the navigation graph between fragments is implemented.
This layer will have access to the necessary frameworks for its correct operation and in turn will have access to the rest of the modules, interacting from its viewmodels with the usecase layer that will return the desired data. For data access we will use the scope of the view, with viewmodelScope we get a CorountineScope.


### Data

It contains the connection **bridge** with the outside and the database. With the use of a repository we access the desired information, taking said information from the **external api** or from the **database** as appropriate. For example, if I want to get a character, I first try to access the database, since access is faster, but if it doesn't find it, I'll launch a request to the api.
Another example is when obtaining new records, since this can only be done by accessing the external repository, but in the process it must store the new records in the database.


### Domain

This layer contains the models in an abstract way and the necessary interfaces for communication. This layer, having completely decoupled from the rest, must have an interface that implements the **data** layer so that there is communication but not a dependency from **domain** to **data** making use of dependency inversion.


### UseCase

Finally, this module will act as a bridge between the **view** layer and the **data** layer. Using the repository provided by data and the **domain** objects and models. In order to be decoupled from **data**, said layer will use the **domain** interface exclusively and not the implementation of **data**.


## Tools and Frameworks

To compose our app we have made use of a series of tools and frameworks that help us reduce time and facilitate work.
To maintain the objects between classes without the responsibility of direct injection of the same, it has been decided to use **Hilt** that contains all the advantages of **Dagger** and includes a series of extra tools that save the development of repeat injections. We can indicate entry points and access the different classes in a very clean and clear way.
For them, some modules have been developed in the different layers, which allow objects to be shared with the different providers.

On the other hand, access to remote information comes from the hand of the **Retrofit** framework, of which we will create an instance in a **Hilt** module to be able to inject it in any part of our app and with the use of the tools that it provides us, we can make requests and save them in data class models.

For information persistence, **Room** has been chosen as the framework. This tool requires the creation of a database, which we will again supply from a **Hilt** module and a so-called **Dao** that contains the input and output requests to the database.

Access to the view data is done using **ViewBinding**, which automatically creates classes for each view .xml file. Thus referencing it, we have access from the corresponding Fragment or Activity class.

The images come from the hand of **Glide**. And as mentioned before, using the Navigation component **Navigation** we can move between fragments by simply designing the graph.

|      Name      | Utility                       |
|----------------|-------------------------------|
|Hilt            |`Dependency Inyection`         |
|Retrofit        |`Http access`                  |
|Room            |`Persistence information`      |
|ViewBinding     |`Access to xml components`     |
|Glide           |`Image processing`             |
|Navigation      |`Navigation between fragments` |
|Flow            |`Issuance of states`           |
|Mockito         |`Tools for testing`            |
|Nhaarman        |`Tools for testing`            |


## Testing

The app has tested the viewmodels, the usecase and the repository. For them, Mockito and Junit have been used.  In addition to the tools provided by **nhaarman** that make it very easy to use mocked objects. The **coverage** in all tested classes is 100%. And through doing 'collect' in the responses of **flow** we are making the different assertions and call verifications.
As a detail within the test, in order to operate in the same **CorountineScope** as the viewmodel, the dispatcher is injected from a **Hilt** module, so we can put the one we want when creating our fake viewmodel.


## Error Control

A basic error control has been created that could be extended by each function that uses it, with a default dialog when we cannot access the information. Since there are different types of errors returned by the repository, we can create a process that distinguishes each returned object and displays a custom message for each response.

## Controls and Tools

A component called **RecyclerViewWithSearchBar** has been developed, which can be used anywhere in the app and allows the possibility of passing a series of functions to it for each available event, such as going to the top of the list, moving by it, or by pressing any button on the search bar. The component frees the view from such control, simplifying the use of it to the simplest functions.

The transformation between models has been developed using the Kotlin extensions, so we can make direct use of these functions from the same object.

With the use of base classes for both views and viewmodels we can unify common functions that could be used in classes that are inherited, such as the treatment of the responses of a call and forgetting to deal with basic errors that can be repeated over time. throughout the app, or the control of visual loading elements for the user.


### Other Explanations

As for access to the api, a public and a private key are necessary; To simplify the test of the app, a Constants.kt class is included that contains some managed by the developer. Ideally, due to security requirements, it would be that these keys were written as a **buildConfig** inside the gradle, for example in ** release** and thus allow each app tester to manage their own user within the Marvel website; but as described, to simplify testing it has been left in this class.

Add that the app doesn't really delete the references, but it changes the state from visble to invisble within the database and every time the list of characters is supplied, it is filtered by visible.

It was decided to separate **usecase** from **domain** to be clearer, but this could be incorporated inside, since the objects it uses are exclusively from **domain**, but if it needed another bridge due to requirements of the project to another language, it would be easier to change.

On the other hand, the app could continue to grow if, when selecting a character, we obtain more data about it from the api, such as the comics in which it appears or its modifications.
