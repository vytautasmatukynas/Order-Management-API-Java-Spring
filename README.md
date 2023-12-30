# Order Management API

This is `Java` `Spring Boot` backend API for `Order Managment Application`.

`Order Managment Application` is designed to streamline and manage the process of handling orders.
It provides features for creating, updating, and tracking orders for efficient order fulfillment.

<br>

API uses `SQL` database and has 3 tables - `orders` `order_items` `users`.

`orders` table stores fundamental information about orders - order number, order name, client name, client phone number,
client email, order term, order status, order price, comments, order update date.

`order_items` table contains more detailed information about each item within order - item name, item code, 
item revision, item count, item price, total price.

`users` table stores information about all users - first name, last name, username, password, role.

<br>

API configurations:

• `CORS configuration` (@CrossOrigin annotation) that enables access for `GET` `POST` `DELETE` and `UPDATE` requests. 
Except for `get all user`, `delete user` and `register new user` requests. 

• `Spring Security` + `JWT` for security and users handling.

• `Spring Security` uses hierarchical architecture for managing `ROLES`. There are 3 `ROLES`:
1. `ROLE_ADMIN` can do every action there is for all `GET` `POST` `DELETE` and `UPDATE` requests. 
2. `ROLE_MANAGER` has same privileges for `GET` `POST` `DELETE` and `UPDATE` requests, just can't `delete` old user and 
`register` new user. 
3. `ROLE_USER` has privileges for `GET` requests, basically `Read-Only` for `orders` and `order_items`, except it has 2 
`POST` requests for `change password` and `register`.

<br>

### `JUnit` test for services (`GET` for now...), mocks 10000 requests:

![img_2.png](readmeImg/img_2.png)

![img_1.png](readmeImg/img_1.png)

<br>

## More details about API functionality:

### Security configuration handling endpoints for different `ROLE` for `users`:

|           Endpoint           |         Role         |
|:----------------------------:|:--------------------:|
|  /api/v1/user/authenticate   |     permitAll()      |
| /api/v1/user/change/password | ADMIN, MANAGER, USER |
|        /api/v1/users         |        ADMIN         |
|    /api/v1/user/register     |        ADMIN         |
|     /api/v1/user/delete      |        ADMIN         |

<br>

### Endpoints for `users`:

`AUTHENTICATE` user endpoint: `/api/v1/user/authenticate`.

<br>

`CHANGE PASSWORD` endpoint: `/api/v1/user/change/password`.

<br>

`GET ALL` users endpoint: `/api/v1/users`.

<br>

`REGISTER` user endpoint: `/api/v1/user/register`.

<br>

`DELETE` user endpoint: `/api/v1/user/delete`.

<br>

### Security configuration handling endpoints for different `ROLE` for `orders`:

|               Endpoint                |         Role         |
|:-------------------------------------:|:--------------------:|
|            /api/v1/orders             | ADMIN, MANAGER, USER |
|        /api/v1/order/{orderId}        | ADMIN, MANAGER, USER |
|   /api/v1/order/search/{orderParam}   | ADMIN, MANAGER, USER |
|           /api/v1/add/order           |    ADMIN, MANAGER    |
|    /api/v1/update/order/{orderId}     |    ADMIN, MANAGER    |
|    /api/v1/delete/order/{orderId}     |    ADMIN, MANAGER    |

<br>

### Endpoints for `orders`:

• `GET ALL` orders and order items endpoint: `/api/v1/orders`.

<br>

• `GET` specific order and order items endpoint: `/api/v1/order/{orderId}`.

<br>

• `Search` orders by `order_code` `order_name` `client` `client_phone_number` `client_email` endpoint: `/api/v1/order/search/{orderParam}`.

• `Search` is case-insensitive and you don't need to provide full name of search parameter.

<br>

• `CREATE` new order endpoint: `/api/v1/add/order`.

• `order_number` and `order_update_date` are generated automatically. `order_price` is calculated automatically, summing up prices of all `order_items`.

• New order is created with an empty `order_items` list.

<br>

• `UPDATE` order endpoint: `/api/v1/update/order/{orderId}`.

• `order_number` remains unchanged, and `order_update_date` is generated automatically.

<br>

• `DELETE` order endpoint: `/api/v1/delete/order/{orderId}`.

<br>

### Security configuration handling endpoints for different `ROLE` for `order_items`:

|                     Endpoint                      |         Role         |
|:-------------------------------------------------:|:--------------------:|
|           /api/v1/order/{orderId}/items           | ADMIN, MANAGER, USER |
|            api/v1/order/item/{itemId}             | ADMIN, MANAGER, USER |
|         /order/{orderId}/items/{itemName}         | ADMIN, MANAGER, USER |
|             /order/{orderId}/add/item             |    ADMIN, MANAGER    |
|            /order/update/item/{itemId}            |    ADMIN, MANAGER    |
|            /order/delete/item/{itemId}            |    ADMIN, MANAGER    |

<br>

### Endpoints for `order_items`:

• `GET` specific `order` all `order_items` endpoint: `/api/v1/order/{orderId}/items`.

<br>

• `GET` `order_item` endpoint: `api/v1/order/item/{itemId}`.

<br>

• `Search` order items by `item_name` endpoint: `/order/{orderId}/items/{itemName}`.

• `Search` is case-insensitive and you don't need to provide full name of search item.

<br>

• `CREATE` new `order_item` to specific `order` endpoint: `/order/{orderId}/add/item`.

• `total_price` is automatically calculated by multiplying `item_count` and `item_price`.

• After creating new `order_item` - `order_price` in `orders` table will be automatically updated.

<br>

• `UPDATE` `order_item` endpoint: `/order/update/item/{itemId}`.

• `total_price` is automatically calculated by multiplying `item_count` and `item_price`.

• After updating `order_item` - `order_price` in `orders` table will be automatically updated.

<br>

• `DELETE` `order_item` endpoint: `/order/delete/item/{itemId}`.

• After deleting `order_item` - `order_price` in `orders` table will be automatically updated.