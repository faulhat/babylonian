(ns babylonian.core
  (:gen-class))

;; Do one iteration of the Babylonian method to get our next guess for the true square root
(defn next-guess [x guess]
  (/ (+ guess (/ x guess)) 2.0))

(defn format-places [x n] (format (str "%." n "f") x))

;; Round x and y to n places and compare them for equality
(defn eq-places [x y n]
  (let [
    x-str (format-places x n)
    y-str (format-places y n)
  ]
    (= (compare x-str y-str) 0)))

;; Round x to n places
(defn to-places [x n] (Double/parseDouble (format-places x n)))

;; Recursive function to find square root to a given number of decimal places
(defn iter-down [x guess places i]
  ;; Where i is the number of iterations so far
  (let [new-guess (next-guess x guess)]
    (println (str "Iteration " (+ i 1) ":"))

    (let [
      x-str (format-places x places)
      guess-str (format-places guess places)
      new-guess-str (format-places new-guess places)
    ]
      (println (str "(" guess-str " + " x-str "/" guess-str ") / 2 = " new-guess-str "\n")))
      
    (if (not (eq-places guess new-guess places))
      (iter-down x new-guess places (+ i 1))
      (println (str "Final guess: " (to-places new-guess places))))))

;; Wrapper function to make a guess to n places starting with an initial random guess.
(defn get-guess [x n]
  (let [init-guess (* (rand) x)]
    (println (str "Initial guess: " init-guess "\n"))
    (iter-down x init-guess n 0)))

(defn -main [& args]
  ;; Ensure we have the right number of command line args
  (let [n-args (count args)]
    (if (or (< n-args 1) (> n-args 2))
      (do
        (if (< n-args 1)
          (println "Error! Not enough command line arguments.")
          (println "Error! Too many command line arguments."))
        (println "\nUsage:")
        (println "  sqrt [radicand] [n_places]")
        (println "\nIf no value is provided for n_places, the root will be found to 4 places by default.")
        (System/exit 1))

      ;; Now, number of args can only be 1 or 2
      (try
        (let [x (Double/parseDouble (first args))]
          (if (= n-args 1)
            (get-guess x 4) ;; Four places by default
            (try ;; Number of args can only be 2
              (let [n (Integer/parseInt (second args))]
                (get-guess x n))
              (catch NumberFormatException _
                (println "Error! n value is not a valid integer.")
                (println (str "Got: " (second args)))
                (System/exit 1)))))
        (catch NumberFormatException _
          (println "Error! x value is not a valid floating-point number.")
          (println (str "Got: " (first args)))
          (System/exit 1))
        (catch Exception e (throw e)))))
  
  (println "Done!"))
