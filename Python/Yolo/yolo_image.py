import cv2
import numpy as np
from ultralytics import YOLO

def main():
    # Initialize the YOLO model (uses the pre-trained COCO model by default)
    model = YOLO('yolo11m.pt')  # 'n' stands for nano, which is lightweight. Options: n, s, m, l, x for increasing model size

    results = model("photo.png")
    print(results)
    frame = cv2.imread("photo.png")

    im_height, im_width = frame.shape[:2]
    res_frame = cv2.resize(frame, (1600, 780))
    res_height, res_width = res_frame.shape[:2]
    height_ratio = res_height / im_height
    width_ratio = res_width / im_width

    for r in results:
        boxes = r.boxes

        for box in boxes:
            # rescale bounding boxes because we reshaped image
            x1, y1, x2, y2 = box.xyxy[0]
            x1, y1, x2, y2 = int(x1 * width_ratio), int(y1 * height_ratio), int(x2 * width_ratio), int(y2 * height_ratio)

            cls = int(box.cls[0])
            class_name = model.names[cls]

            conf = float(box.conf[0])
            conf_percentage = int(conf * 100)

            # draw bounding box on the image
            color = (65, 105, 225)
            cv2.rectangle(res_frame, (x1, y1), (x2, y2), color, 2)

            # label box
            label = f"{class_name.upper()} {conf_percentage}%"
            cv2.putText(res_frame, label, (x1, y1 - 10), 
                        cv2.FONT_HERSHEY_SIMPLEX, 0.7, color, 2)

    cv2.imshow('YOLO11m Object Detection', res_frame)
    while True:
        if cv2.waitKey(1) & 0xFF == ord('q'):
            break
    cv2.destroyAllWindows()

if __name__ == "__main__":
    main()
