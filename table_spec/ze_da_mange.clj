(require '[table-spec.core :as t])

;; (require '[clojure.spec :as s])

(-> {:connection-uri "jdbc:postgresql://postgres:5432/postgres?user=arthur&password=egide" :schema "public"}
    (t/tables)
    (t/register))

;; (s/exercise :table/lol)
;; (s/exercise :lol/id)



