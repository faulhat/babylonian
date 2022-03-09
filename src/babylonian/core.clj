(ns babylonian.core
  (:gen-class))

;; Do one iteration of the Babylonian method to get our next guess for the true square root
(defn next-guess [x guess]
  (/ (+ guess (/ x guess)) 2.0))

;; Recursive function to make a given number of guesses
(defn iter-down [x guess upper i]
  (println (str "Guess " (+ i 1) "/" upper ": " guess))
  (if (< i (- upper 1))
    (iter-down x (next-guess x guess) upper (+ i 1))
    (println "Done!")))

;; Wrapper function to make n guesses starting with a random guess.
(defn n-guesses [x n]
  (iter-down x (* (rand) x) n 0))

;; Utility function to fail after printing a message.
(defn fatalln [msg]
  (println msg)
  (System/exit 1))

(defn -main [& args]
  (if (< (count args) 1)
      (println "Error! Not enough command line arguments supplied.")
      (try
        (def x (Double/parseDouble (first args)))
        (if (= (count args) 1)
          (n-guesses x 10) ;; Make ten guesses by default
          (if (= (count args) 2) ;; If a value for n is supplied
            (try
                (def n (Integer/parseInt (second args)))
                (n-guesses x n)
                (catch NumberFormatException _
                  (fatalln (str "Error! n value is not a valid integer.\nGot: " (second args)))))
            (fatalln "Error! Too many command line arguments supplied.")))
        (catch NumberFormatException _
          (fatalln (str "Error! x value is not a valid floating-point number.\nGot: " (first args))))
        (catch Exception e (throw e)))))
