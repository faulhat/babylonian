(ns babylonian.core
  (:gen-class))

(defn next-guess [x last-guess]
  (/ (+ last-guess (/ x last-guess)) 2.0))

(defn iter-down [x last-guess upper i]
  (if (= i upper)
    (println "Done!")
    (do
      (def guess (next-guess x last-guess))
      (println (str "Guess " (+ i 1) "/" upper ": " guess))
      (iter-down x guess upper (+ i 1)))))

(defn -main [& args]
  (if (< (count args) 2)
      (println "Error! Not enough command line arguments supplied.")
      (try
        (def x (Double/parseDouble (first args)))
        (try
            (def i (Integer/parseInt (second args)))
            (def guess (* (rand) x))
            (println (str "Random guess: " guess))
            (iter-down x guess i 0)
            (catch NumberFormatException _
              (println "Error! i value is not a valid integer.")
              (println (str "Got: " (second args)))
              (System/exit 1)))
        (catch NumberFormatException _
          (println "Error! x value is not a valid floating-point number.")
          (println (str "Got: " (first args)))
          (System/exit 1))
        (catch Exception e (throw e)))))
