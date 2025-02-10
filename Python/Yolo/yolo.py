import cv2
import numpy as np
from ultralytics import YOLO

def main(cap):
    # Initialize the YOLO model (uses the pre-trained COCO model by default)
    model = YOLO('yolo11m.pt')  # 'n' stands for nano, which is lightweight. Options: n, s, m, l, x for increasing model size

    # Open webcam
    results = model("path/to/bus.jpg")
    # Check if the webcam is opened correctly
    if not cap.isOpened():
        raise IOError("Cannot open webcam")

    # Main detection loop
    while True:
        # Read frame from the webcam
        success, frame = cap.read()
        
        if not success:
            print("Failed to grab frame")
            break

        # Run YOLOv8 inference on the frame
        results = model(frame, stream=True)

        # Visualize the results on the frame
        for r in results:
            # Bounding boxes
            boxes = r.boxes

            for box in boxes:
                # Bounding box coordinates
                x1, y1, x2, y2 = box.xyxy[0]
                x1, y1, x2, y2 = int(x1), int(y1), int(x2), int(y2)

                # Class name
                cls = int(box.cls[0])
                class_name = model.names[cls]

                # Confidence
                conf = float(box.conf[0])
                conf_percentage = int(conf * 100)

                # Draw bounding box
                color = (65, 105, 225)  # Royal Blue
                cv2.rectangle(frame, (x1, y1), (x2, y2), color, 2)

                # Put class name and confidence
                label = f"{class_name.upper()} {conf_percentage}%"
                cv2.putText(frame, label, (x1, y1 - 10), 
                            cv2.FONT_HERSHEY_SIMPLEX, 0.7, color, 2)

        # Resize frame for display
        frame = cv2.resize(frame, (1600, 780))
        
        # Display the frame
        cv2.imshow('YOLOv8 Object Detection', frame)

        # Break loop on 'q' key press
        if cv2.waitKey(1) & 0xFF == ord('q'):
            break

    # Release resources
    cap.release()
    cv2.destroyAllWindows()

if __name__ == "__main__":
    cap = cv2.VideoCapture(0)
    cap.set(cv2.CAP_PROP_FOURCC, cv2.VideoWriter_fourcc('M', 'J', 'P', 'G'))
    main(cap)
