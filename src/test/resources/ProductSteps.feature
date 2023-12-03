Feature: Product Management

  Scenario: Add a new product
    Given the client wants to add a new product
    When the client provides product details:
      | name        | description          | price | weight | category  | quantity |
      | TEST | TEST   | 20.0  | 1.5    | Electronics | 150   |
    Then the product is successfully added

  Scenario: Retrieve all products
    Given the following products in the database:
      | name           | description            | price | weight | category    | quantity |
      | New Product    | A new product entry    | 20.0  | 1.5    | Electronics | 150      |
      | New Product2   | A new product entry2   | 120.0 | 15     | Beauty      | 20       |
    When the client wants to fetch all products
    Then a list of products is successfully retrieved

  Scenario: Retrieve a product by ID
    Given the following products in the database to pick from:
      | name           | description            | price | weight | category    | quantity |
      | New Product    | A new product entry    | 20.0  | 1.5    | Electronics | 150      |
      | New Product2   | A new product entry2   | 120.0 | 15     | Beauty      | 20       |
    When the client provides the product ID to get
    Then the product with passed ID is successfully retrieved

  Scenario: Update an existing product
    Given the client product in a database:
      | name           | description            | price | weight | category    | quantity |
      | New Product    | A new product entry    | 20.0  | 1.5    | Electronics | 100      |
    When the client provides updated product details:
      |id            | name            | description              | price | weight | category    | quantity |
      | expected id  | Updated Product | An updated product entry | 25.0  | 2.0    | Electronics | 50      |
    Then the product is successfully updated

  Scenario: Delete an existing product
    Given the following products in the database client can delete:
      | name           | description            | price | weight | category    | quantity |
      | New Product    | A new product entry    | 20.0  | 1.5    | Electronics | 150      |
      | New Product2   | A new product entry2   | 120.0 | 15     | Beauty      | 20       |
    When the client provides the product ID to delete
    Then the product with provided ID is successfully deleted

  Scenario: Retrieve a product by wrong ID
    When the client provides wrong product ID to fetch
    Then an error is returned

  Scenario: Add a new product with wrong data
    Given the client wants to add a new product with wrong data
    When the client provides wrong product details:
      | name           | description            | price | weight | category    | quantity |
      | New Product    | A new product entry    | -1  | 1.5    | Electronics | 150      |
    Then a product creation error is returned
