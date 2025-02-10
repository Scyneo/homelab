import cv2
import numpy as np

cap = cv2.VideoCapture(0) #pobieramy video z kamerki
whT = 320 # parametr wysokości i szerokości przetwarzanego obrazu
confThreshold = 0.5 #próg prawd.
nmsThreshold = 0.3 #próg nms

#otwieramy plik z nazwami klas i zapisujemy dane z pliku w liście
classesFile = 'coco.names'
classNames = []
with open(classesFile, 'rt') as f:
    classNames = f.read().rstrip('\n').split('\n')

#konstruujemy naszą sieć, zaczynamy od zaimportowania pliku cfg (zawierającego architekturę sieci) i wag
modelConfiguration = 'yolov3.cfg'
modelWeights = 'yolov3.weights'

net = cv2.dnn.readNetFromDarknet(modelConfiguration, modelWeights)
#wybieramy backend (część systemu komputerowego lub aplikacji odpowiedzialnej za przechowywanie i manipulację danymi) i urządzenie do obliczeń
net.setPreferableBackend(cv2.dnn.DNN_BACKEND_OPENCV)
net.setPreferableTarget(cv2.dnn.DNN_TARGET_CPU)


def FindObjects(outputs, img):
    hT, wT, cT = img.shape
    #kiedy znajdziemy ramkę z wystarczjąco dobrymi wynikami, umieszczamy jej dane w listach
    bbox = []
    classIDs = []
    confs = []

    #szukamy jaka klasa obiektu jest najbardziej prawd. dla poszczególnych ramek
    for output in outputs:
        for detection in output:
            #pomijamy 5 pierwszych kolumn bo nie zawierają one danych o klasyfikacji obietków
            scores = detection[5:]
            classID = np.argmax(scores)
            confidence = scores[classID]
            #jeżeli prawd. przekracza próg, pobieramy parametry i przekazujemy je do odp. list
            if confidence > confThreshold:
                w, h = int(detection[2]*wT) , int(detection[3]*hT)
                x, y = (int((detection[0]*wT) - w/2)) , (int((detection[1]*hT) - h/2))
                bbox.append([x,y,w,h])
                classIDs.append(classID)
                confs.append(float(confidence))
    
    #no maximal suppresion - funkcja która zapobiega wielokrotnemu wykrywaniu tego samego obiektu przez różne ramki
    indicies = cv2.dnn.NMSBoxes(bbox, confs, confThreshold, nmsThreshold)
    #każdemu wykrytemu obiektowi przypisujemy ramkę z nazwą
    for i in indicies:
        box = bbox[i]
        x, y, w, h = box[0], box[1], box[2], box[3]
        cv2.rectangle(img, (x,y), (x+w, y+h), (65,105,225), 2)
        cv2.putText(img, f'{classNames[classIDs[i]].upper()} {int(confs[i]*100)}%',
                    (x+10,y-10), cv2.FONT_HERSHEY_SIMPLEX, 0.7, (65,105,225), 2)

#pętla ta odczytuje pobrany obraz kamerki, przekazuje go do sieci w odpowiednim formacie i wydobywa dane wyjściowe
while True:
    success, img = cap.read()

    #sieć nie jest w stanie odczytać obrazu, dlatego musimy przerobić go na format, który sieć jest w stanie przetworzyć tj. blob
    blob = cv2.dnn.blobFromImage(img, 1/255, (whT, whT), [0,0,0], 1 ,crop = False)
    net.setInput(blob)

    #wydobywamy nazwy warstw wyjściowych i przekazujemy je dalej do sieci, aby wydobyć z nich dane wyjściowe
    outputNames = list(net.getUnconnectedOutLayersNames())
    outputs = list(net.forward(outputNames))
    
    #kolumny danych wyjściowych to: cx, cy, w, h, pewność obiektu w ramce, następne 80 kolumn to prawdopodobieństwo danej klasy obietku w ramce
    # print(outputs[0].shape)
    # print(outputs[1].shape)
    # print(outputs[2].shape)
    # print(outputs[0][0])

    FindObjects(outputs, img)
    img = cv2.resize(img, (1600,780))    
    cv2.imshow('YOLO-opencv', img)
    cv2.waitKey(1)
    
    