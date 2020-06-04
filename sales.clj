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
    (println "")
    (println "Enter an option?")
  )

  (defn printCustomerRecord [customerRecord]
    (println (customerRecord :custID) ": [" (customerRecord :name) "," (customerRecord :address) "," (customerRecord :phoneNumber) "]")
  )

  (defn printCustomersMap [customerMaps]
    (dorun (map printCustomerRecord (sort-by :custID customerMaps)))
  )

  (defn printProductRecord [productRecord]
    (println (productRecord :prodID) ": [" (productRecord :itemDescription) "," (productRecord :unitCost) "]")
  )

  (defn printProductsMap [productMaps]
    (dorun (map printProductRecord (sort-by :prodID productMaps)))
  )

  (defn printSalesRecord [salesRecord]
    (println (salesRecord :salesID) ": [" (salesRecord :name) "," (salesRecord :itemDescription) "," (salesRecord :itemCount) "]")
  )

  (defn printSalesMap [salesMaps]
    (dorun (map printSalesRecord (sort-by :salesID salesMaps)))
  )

  (defn runUserFunctionality [customerMaps productMaps salesMaps inpChoice]
    (cond
      ; (= inpChoice "1") (println "Selected 1")
      ; (= inpChoice "2") (println "Selected 2")
      ; (= inpChoice "3") (println "Selected 3")
      ; (= inpChoice "4") (println "Selected 4")
      ; (= inpChoice "5") (println "Selected 5")
      (= inpChoice "1") (printCustomersMap customerMaps)
      (= inpChoice "2") (printProductsMap productMaps)
      (= inpChoice "3") (printSalesMap salesMaps)
      (= inpChoice "4") (printCustomersMap customerMaps)
      (= inpChoice "5") (printCustomersMap customerMaps)
    )
  )

  (defn handleUserInput [customerMaps productMaps salesMaps userInp]
    (if ( or (= "1" userInp) (= "2" userInp) (= "3" userInp) (= "4" userInp) (= "5" userInp))
      (do (runUserFunctionality customerMaps productMaps salesMaps userInp)
          (printMenu)
          (def userChoice (read-line))
          (handleUserInput customerMaps productMaps salesMaps userChoice)
      )
      (if (= "6" userInp)
        (do (println "Good Bye!!!!"))
        (do (println "Kindly enter a valid option...")
            (printMenu)
            (def userChoice (read-line))
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
    (println "Successfully loaded data files!")
    (printMenu)
    (def userInp (read-line))
    (handleUserInput customerMaps productMaps salesMaps userInp)
  )

  (launchSalesApp)
