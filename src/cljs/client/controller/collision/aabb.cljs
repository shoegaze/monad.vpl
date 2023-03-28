(ns client.controller.collision.aabb)


(def *debug-draw?* false)

(defn make-aabb
  "Creates an axis-aligned bounding box given two points in the same space. Ensures start <= end."
  [start end]
  (let [[x-1 y-1] start
        [x-2 y-2] end
        start [(js/Math.min x-1 x-2)
               (js/Math.min y-1 y-2)]
        end   [(js/Math.max x-1 x-2)
               (js/Math.max y-1 y-2)]]
    {:start start
     :end   end  }))

(defn size [aabb]
  (let [{[x-1 y-1] :start
         [x-2 y-2] :end  } aabb
        dx (- x-2 x-1)
        dy (- y-2 y-1)]
    [dx dy]))

(defn inside? [aabb [x y]]
  (let [{[x-1 y-1] :start
         [x-2 y-2] :end  } aabb]
    (and (< x-1 x x-2)
         (< y-1 y y-2))))

(defn draw [ctx aabb]
  :TODO)


;(defmulti aabb :type)
;
;(defmethod aabb :camera []
;  :TODO)
;
;(defmethod aabb :instance []
;  :TODO)
;
;(defmethod aabb :pin []
;  :TODO)
