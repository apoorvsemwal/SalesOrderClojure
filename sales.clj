  (ns com.SalesApp)

  (defn prepareCustomersVectorOfMaps [dataRecord]
    (def customerRecord (clojure.string/split dataRecord #"\|"))
    (hash-map :custID (get customerRecord 0)
              :name (get customerRecord 1)
              :address (get customerRecord 2)
              :phoneNumber (get customerRecord 3)
    )
  )

  (defn readCustomersDataFile []
    (map prepareCustomersVectorOfMaps (clojure.string/split-lines (slurp "cust.txt")))
  )

  (defn prepareProductsVectorOfMaps [dataRecord]
    (def productRecord (clojure.string/split dataRecord #"\|"))
    (hash-map :prodID (get productRecord 0)
              :itemDescription (get productRecord 1)
              :unitCost (Double/parseDouble (get productRecord 2))
    )
  )

  (defn readProductsDataFile []
    (map prepareProductsVectorOfMaps (clojure.string/split-lines (slurp "prod.txt")))
  )

  (defn prepareSalesVectorOfMaps [dataRecord]
    (def salesRecord (clojure.string/split dataRecord #"\|"))
    (hash-map :salesID (get salesRecord 0)
              :custID (get salesRecord 1)
              :prodID (get salesRecord 2)
              :itemCount (Integer/parseInt (get salesRecord 3))
    )
  )

  (defn readSalesDataFile []
    (map prepareSalesVectorOfMaps (clojure.string/split-lines (slurp "sales.txt")))
  )


  (defn printMenu []
    (newline)
    (println "*** Sales Menu ***")
    (println "------------------")
    (newline)
    (println "1. Display Customer Table")
    (println "2. Display Product Table")
    (println "3. Display Sales Table")
    (println "4. Total Sales for Customer")
    (println "5. Total Count for Product")
    (println "6. Exit")
    (newline)
    (println "Enter an option?")
  )

  (defn printCustomerRecord [customerRecord]
    (println (customerRecord :custID) ": [" (customerRecord :name) "," (customerRecord :address) "," (customerRecord :phoneNumber) "]")
  )

  (defn printCustomersMap [customerMaps]
    (mapv printCustomerRecord (sort-by :custID customerMaps))
  )

  (defn printProductRecord [productRecord]
    (println (productRecord :prodID) ": [" (productRecord :itemDescription) "," (productRecord :unitCost) "]")
  )

  (defn printProductsMap [productMaps]
    (mapv printProductRecord (sort-by :prodID productMaps))
  )

  (defn updateSalesMap [salesMaps customerMaps productMaps]
    (letfn [(linkSalesToCustomerAndProductAndPrice [salesRecord]
              (def salesCustomer (vec (filter (fn [customerRecord] (= (customerRecord :custID) (salesRecord :custID))) customerMaps)))
              (def salesProduct (vec (filter (fn [productRecord] (= (productRecord :prodID) (salesRecord :prodID))) productMaps)))
              (hash-map :salesID (salesRecord :salesID)
                        :name ((get salesCustomer 0) :name)
                        :itemDescription ((get salesProduct 0) :itemDescription)
                        :unitCost ((get salesProduct 0) :unitCost)
                        :itemCount (salesRecord :itemCount)
              )
           )]
           (map linkSalesToCustomerAndProductAndPrice salesMaps)
    )
  )

  (defn printSalesRecord [salesRecord]
    (println (salesRecord :salesID) ": [" (salesRecord :name) "," (salesRecord :itemDescription) "," (salesRecord :itemCount) "]")
  )

  (defn printSalesMap [salesMaps]
    (mapv printSalesRecord (sort-by :salesID salesMaps))
  )

  (defn printTotalSaleValueForCustomer [salesMaps]
    (println "Enter the customer's name whose total sale value to find.")
    (def custName (read-line))
    (newline)
    (def salesCustomer
      (vec
        (filter (fn [salesRecord] (= custName (salesRecord :name))) salesMaps)
      )
    )
    (if (empty? salesCustomer)
      (do
        (println "Error: Customer" custName "not found in database. Names are case sensitive. Please check and try again...")
      )
      (do
        (def totalSaleValueCustomer
          (mapv (fn [salesRecord] (assoc salesRecord :saleValue (* (:itemCount salesRecord) (:unitCost salesRecord)))) salesCustomer)
        )
        (println custName ": $" (reduce + (mapv :saleValue totalSaleValueCustomer)))
      )
    )

  )

  (defn printTotalSoldItemCount [salesMaps]
    (newline)
    (println "Enter the item's name for which total sale count to find.")
    (def prodName (read-line))
    (def salesItem
      (vec
        (filter (fn [salesRecord] (= prodName (salesRecord :itemDescription))) salesMaps)
      )
    )
    (if (empty? salesItem)
      (do
        (println "Error: Item" prodName "not found in database. Names are case sensitive. Please check and try again...")
      )
      (do
        (println prodName ":" (reduce + (mapv :itemCount salesItem)))
      )
    )
  )

  (defn runUserFunctionality [customerMaps productMaps salesMaps inpChoice]
    (cond
      (= inpChoice "1") (printCustomersMap customerMaps)
      (= inpChoice "2") (printProductsMap productMaps)
      (= inpChoice "3") (printSalesMap salesMaps)
      (= inpChoice "4") (printTotalSaleValueForCustomer salesMaps)
      (= inpChoice "5") (printTotalSoldItemCount salesMaps)
    )
  )

  (defn handleUserInput [customerMaps productMaps salesMaps userInp]
    (if ( or (= "1" userInp) (= "2" userInp) (= "3" userInp) (= "4" userInp) (= "5" userInp))
      (do (runUserFunctionality customerMaps productMaps salesMaps userInp)
          (printMenu)
          (def userChoice (read-line))
          (newline)
          (handleUserInput customerMaps productMaps salesMaps userChoice)
      )
      (if (= "6" userInp)
        (do (println "Good Bye!!!!"))
        (do (println "Kindly enter a valid option...")
            (printMenu)
            (def userChoice (read-line))
            (newline)
            (handleUserInput customerMaps productMaps salesMaps userChoice)
        )
      )
    )
  )

  (defn launchSalesApp []
    (newline)
    (println "Loadin data files......")
    (def customerMaps (readCustomersDataFile))
    (def productMaps (readProductsDataFile))
    (def salesMaps (readSalesDataFile))
    (def salesMapsWithCustomerAndProduct (updateSalesMap salesMaps customerMaps productMaps))
    (println "Successfully loaded data files!")
    (printMenu)
    (def userInp (read-line))
    (newline)
    (handleUserInput customerMaps productMaps salesMapsWithCustomerAndProduct userInp)
  )

  (launchSalesApp)
