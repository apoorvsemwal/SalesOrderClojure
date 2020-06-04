    ;cd '.\Concor\Subjects\Term6-Summer-2020\Comparative-COMP 6411\Assignments\A2\SalesOrderClojure\'
    ;Run link chlorine to REPL first using Powershell
    ;clj -J'-Dclojure.server.repl={:port,5555,:accept,clojure.core.server/repl}'
    ;(ns com.SalesApp)

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
    (println "")
    (println "*** Sales Menu ***")
    (println "------------------")
    (println "")
    (println "1. Display Customer Table")
    (println "2. Display Product Table")
    (println "3. Display Sales Table")
    (println "4. Total Sales for Customer")
    (println "5. Total Count for Product")
    (println "6. Exit")
    (println "")
    (println "Enter an option?")
    (str)
  )

  (defn runUserFunctionality [customerVector productVector salesVector inpChoice]
    (cond
        (= inpChoice "1") (println "Selected 1")
        (= inpChoice "2") (println "Selected 2")
        (= inpChoice "3") (println "Selected 3")
        (= inpChoice "4") (println "Selected 4")
        (= inpChoice "5") (println "Selected 5")
    )
  )

  (defn printCustomerRecord [customerRecord]
    (println (customerRecord :custID) ": [" (customerRecord :name) "," (customerRecord :address) "," (customerRecord :phoneNumber) "]")
    (str)
  )

  (defn printCustomersMap [customerMaps]
    (map printCustomerRecord (sort-by :custID customerMaps))
  )

  (defn printProductRecord [productRecord]
    (println (productRecord :prodID) ": [" (productRecord :itemDescription) "," (productRecord :unitCost) "]")
    (str)
  )

  (defn printProductsMap [productMaps]
    (map printProductRecord (sort-by :prodID productMaps))
  )

  (defn printSalesRecord [salesRecord]
    (println (salesRecord :salesID) ": [" (salesRecord :name) "," (salesRecord :itemDescription) "," (salesRecord :itemCount) "]")
    (str)
  )

  (defn printSalesMap [salesMaps]
    (map printSalesRecord (sort-by :salesID salesMaps))
  )

  (defn handleUserInput [customerVector productVector salesVector userInp]
    (if ( or (= "1" userInp) (= "2" userInp) (= "3" userInp) (= "4" userInp) (= "5" userInp))
      (do (runUserFunctionality customerVector productVector salesVector userInp)
          (printMenu)
          (def userChoice (read-line))
          (handleUserInput customerVector productVector salesVector userChoice)
      )
      (if (= "6" userInp)
        (do (println "Good Bye!!!!"))
        (do (println "Kindly enter a valid option...")
            (printMenu)
            (def userChoice (read-line))
            (handleUserInput customerVector productVector salesVector userChoice)
        )
      )
    )
  )

  (defn launchSalesApp []
    (println "Loadin data files......")
    (def customerVector (readCustomersDataFile))
    (def productVector (readProductsDataFile))
    (def salesVector (readSalesDataFile))
    (println "Successfully loaded data files!")
    (printMenu)
    (def userInp (read-line))
    (handleUserInput customerVector productVector salesVector userInp)
  )

  (launchSalesApp)
