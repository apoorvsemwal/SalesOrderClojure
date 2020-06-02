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
