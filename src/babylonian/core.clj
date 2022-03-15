(ns babylonian.core
  (:gen-class))

;; Do one iteration of the Babylonian method to get our next guess for the true square root
(defn next-guess [x guess]
  (/ (+ guess (/ x guess)) 2.0))

;; Recursive function to do a given number of iterations.
(defn iter-down [x guess upper i]
  (let [new-guess (next-guess x guess)]
    (println (str "Iteration " i ":"))
    (println (str "(" guess " + " x "/" guess ") / 2 = " new-guess "\n"))
    (if (< i upper)
      (iter-down x new-guess upper (+ i 1)))))

;; Wrapper function to make n guesses starting with an initial random guess.
(defn n-guesses [x n]
  (let [init-guess (* (rand) x)]
    (println (str "Initial guess: " init-guess "\n"))
    (iter-down x init-guess n 1)))

(defn -main [& args]
  ;; Ensure we have the right number of command line args
  (let [n-args (count args)]
    (if (or (< n-args 1) (> n-args 2))
      (do
        (if (< n-args 1)
          (println "Error! Not enough command line arguments.")
          (println "Error! Too many command line arguments."))
        (println "\nUsage:")
        (println "  sqrt [radicand] [n_iterations]")
        (println "\nIf no value is provided for n_iterations, 10 iterations will be run by default.")
        (System/exit 1))

      ;; Now, number of args can only be 1 or 2
      (try
        (let [x (Double/parseDouble (first args))]
          (if (= n-args 1)
            (n-guesses x 10) ;; Do ten iterations by default.
            (try ;; Number of args can only be 2
              (let [n (Integer/parseInt (second args))]
                (n-guesses x n))
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
