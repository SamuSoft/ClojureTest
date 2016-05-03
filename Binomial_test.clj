(declare Binom Bin B BArray Looper Looper2)



;; Fixed, it now works
(def Binom (fn [r i n k] 
	(int 
		(if (= i (+ k 1)) 
			r 
			(Binom 
				(* r (/ (int (+ (- n i) 1)) (int i)))
				(+ i 1) 
				n 
				k)
			)
		)
	)
)


(def Bin (fn [n k] (Binom 1 1 n k)))


(def BArray 
	(fn [x] 
		(reduce 
			(fn [bs m] 
				(vec 
					(concat 
						bs 
						[(Bernoulli bs m)]
					)
				)
			)
			[1]
			(range 1 (+ x 1))
		)
	)
)

(def sum
	(fn [array]
		(reduce + array)))


(def step
	(fn [bs m]
		(fn [k]
			(- 
				(* 
					(Bin 
						(+ m 1) 
						k
					) 
					(get bs k)
				)
			)
		)
	)
)

(def Bernoulli
	(fn [bs m]
		(/ 
			(sum 
				(map 
					(step bs m)
					(range 0 m)
				)		
			)
			(+ m 1)
		)	
	)
)

;;function B(n)
;;	B[0] ← 1
;;	for m ← 1 to n do
;;		B[m] ← 0
;;		for k ← 0 to m − 1 do
;;			B[m] ← B[m] − BINOM(m + 1, k) · B[k]
;;		B[m] ← B[m]/(m + 1)
;;	return B[n]


;;function BINOM(n, k)
;;r ← 1
;; for i ← 1 to k do
;; r ← r · (n − i + 1)/i
;; return r


(def Looper 
	(fn [array indexM indexK limit ]
		(if (or 
				(= indexM limit) 
				(> indexM limit)
			)
			array;;(pprint array)
			(do 
				(println array) 
				(Looper2 
					array 
					indexM 
					indexK 
					limit
				)
			)
		)
	)
)

(def Looper2
	(fn [array indexM indexK limit]
		(if (= indexK indexM)
			(Looper 
				(assoc 
					(conj 
						array 
						0
					) 
					indexM
					;;B[m] ← B[m]/(m + 1)
					(/ (get array indexM) (int (+ indexM 1)))
				)
				(+ indexM 1) 0 limit
			)
			(Looper 
				(assoc 
					array 
					indexM 
					(- 
						(get 
							array 
							indexM
						)
						(* 
							(Bin 
								(+ indexM 1) 
								indexK
							)
							(get 
								array 
								indexK
							)
						)
					)
				)
				indexM
				(+ indexK 1)
				limit
			)
		)
	)
)





