(ns babylonian.core
  (:gen-class))

;; Do one iteration of the Babylonian method to get our next guess for the true square root
(defn next-guess [x guess]
  (/ (+ guess (/ x guess)) 2.0))

;; Multiply by 10^n and truncate to int
(defn to-places [x n]
  (int (* x (Math/pow 10 n))))

;; Recursive function to find square root to a given number of decimal places
(defn iter-down [x guess places i]
  ;; Where i is the number of iterations so far
  (let [new-guess (next-guess x guess)]
    (println (str "Iteration " (+ i 1) ":"))
    (println (str "(" guess " + " x "/" guess ") / 2 = " new-guess "\n"))
    (let [truncated (to-places new-guess places)]
      (if (not= (to-places guess places) truncated)
        (iter-down x new-guess places (+ i 1))
        (println (str "Final guess: " (/ truncated (Math/pow 10.0 places))))))))

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
