(ns babylonian.core
  (:gen-class))

;; Do one iteration of the Babylonian method to get our next guess for the true square root
(defn next-guess [x guess]
  (/ (+ guess (/ x guess)) 2.0))

;; Recursive function to do a given number of iterations.
(defn iter-down [x guess upper i]
  (def new-guess (next-guess x guess))
  (println (str "Iteration " i ":"))
  (println (str "(" guess " + " x "/" guess ") / 2 = " new-guess "\n"))
  (if (< i upper)
    (iter-down x new-guess upper (+ i 1))))

;; Wrapper function to make n guesses starting with an initial random guess.
(defn n-guesses [x n]
  (def init-guess (* (rand) x))
  (println (str "Initial guess: " init-guess "\n"))
  (iter-down x init-guess n 0))

;; Utility function to fail after printing a message.
(defn fatalln [msg]
  (println msg)
  (System/exit 1))

(defn -main [& args]
  ;; Ensure we have the right number of command line args
  (def n-args (count args))
  (if (or (< n-args 1) (> n-args 2))
    (do
      (if (< n-args 1)
        (println "Error! Not enough command line arguments.")
        (println "Error! Too many command line arguments."))
      (fatalln "\nUsage:\n  sqrt [radicand] [n_iterations]\n\nIf no value is provided for n_iterations, 10 iterations will be run by default.")))

  ;; Now, number of args can only be 1 or 2
  (try
    (def x (Double/parseDouble (first args)))
    (if (= n-args 1)
      (n-guesses x 10) ;; Do ten iterations by default.
      (try ;; Number of args can only be 2
        (def n (Integer/parseInt (second args)))
        (n-guesses x n)
        (catch NumberFormatException _
          (fatalln (str "Error! n value is not a valid integer.\nGot: " (second args))))))
    (catch NumberFormatException _
      (fatalln (str "Error! x value is not a valid floating-point number.\nGot: " (first args))))
    (catch Exception e (throw e)))
  
  (println "Done!"))
