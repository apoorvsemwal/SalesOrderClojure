    ;cd '.\Concor\Subjects\Term6-Summer-2020\Comparative-COMP 6411\Assignments\A2\SalesOrderClojure\'
    ;Run link chlorine to REPL first using Powershell
    ;clj -J'-Dclojure.server.repl={:port,5555,:accept,clojure.core.server/repl}'
    ;(ns com.SalesApp)

  (ns com.SalesApp)

  (defn readCustomersDataFile []
    (with-open [rdr (clojure.java.io/reader "cust.txt")] (reduce conj [] (line-seq rdr)))
  )

  (defn readProductsDataFile []
    (with-open [rdr (clojure.java.io/reader "prod.txt")] (reduce conj [] (line-seq rdr)))
  )

  (defn readSalesDataFile []
    (with-open [rdr (clojure.java.io/reader "sales.txt")] (reduce conj [] (line-seq rdr)))
  )

  (defn printMenu []
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
  )

  (defn printCustomersTable []
    (println "Customers")
  )

  (defn printProductsTable []
    (println "Products")
  )

  (defn printSalesTable []
    (println "Sales")
  )

  (defn launchSalesApp []
    (println "Loadin data files......")
    (def customerVector (readCustomersDataFile))
    (def productVector (readProductsDataFile))
    (def salesVector (readSalesDataFile))
    (println "Successfully loaded data files!")
    (def choice (atom 0))
    (while (not= @choice 6)
      (printMenu)
      (def userInp (read-line))
      (cond
        (= (clojure.string/trim userInp) "1") (printCustomersTable)
        (= (clojure.string/trim userInp) "2") (printProductsTable)
        (= (clojure.string/trim userInp) "3") (printSalesTable)
        (= (clojure.string/trim userInp) "4") (println "Selected 4")
        (= (clojure.string/trim userInp) "5") (println "Selected 1")
        (= (clojure.string/trim userInp) "6") (reset! choice 6)
        :else (println "Please enter a valid option...")
      )
    )
    (println "Good Bye!!!!")
  )

  (launchSalesApp)
