import tensorflow as tf
import tensorflow_datasets as tfds
import matplotlib.pyplot as plt

# Set random seed for reproducibility
tf.random.set_seed(42)

# Dataset parameters
IMG_HEIGHT = 224
IMG_WIDTH = 224
BATCH_SIZE = 32
NUM_CLASSES = 2

def format_image(image, label):
    image = tf.cast(image, tf.float32) / 255.0  # Normalize the pixel values
    image = tf.image.resize(image, (IMG_HEIGHT, IMG_WIDTH))  # Resize to the desired size
    return image, label

def load_data(data_dir):
    """
    Load and preprocess image data using ImageDataGenerator
    """
    # Data augmentation and preprocessing for training
    (train_data, validation_data, test_data), info = tfds.load('cats_vs_dogs',
                                                            split=['train[:80%]', 'train[80%:90%]', 'train[90%:]'],
                                                            with_info=True,
                                                            as_supervised=True)

    train_data = train_data.map(format_image).shuffle(1000).batch(BATCH_SIZE)
    validation_data = validation_data.map(format_image).batch(BATCH_SIZE)
    test_data = test_data.map(format_image).batch(BATCH_SIZE)

    return train_data, validation_data, test_data
    # train_datagen = ImageDataGenerator(
    #     rescale=1./255,
    #     rotation_range=20,
    #     width_shift_range=0.2,
    #     height_shift_range=0.2,
    #     shear_range=0.2,
    #     zoom_range=0.2,
    #     horizontal_flip=True,
    #     validation_split=0.2  # 20% for validation
    # )

    # # Load training data
    # train_generator = train_datagen.flow_from_directory(
    #     data_dir,
    #     target_size=(IMG_HEIGHT, IMG_WIDTH),
    #     BATCH_SIZE=BATCH_SIZE,
    #     class_mode='categorical',
    #     subset='training'
    # )

    # # Load validation data
    # validation_generator = train_datagen.flow_from_directory(
    #     data_dir,
    #     target_size=(IMG_HEIGHT, IMG_WIDTH),
    #     BATCH_SIZE=BATCH_SIZE,
    #     class_mode='categorical',
    #     subset='validation'
    # )

    # return train_generator, validation_generator

def create_transfer_model():
    # Load pre-trained MobileNetV2 model
    base_model = tf.keras.applications.MobileNetV2(
        weights='imagenet', 
        include_top=False, 
        input_shape=(IMG_HEIGHT, IMG_WIDTH, 3))
    base_model.trainable = False
    model = tf.keras.Sequential([
        base_model,
        tf.keras.layers.GlobalAveragePooling2D(),
        # layers.Dense(128, activation='relu'),
        # layers.Dropout(0.5),
        tf.keras.layers.Dense(1, activation='softmax')
    ])

    model.compile(
        optimizer=tf.keras.optimizers.Adam(learning_rate=0.001),
        loss='categorical_crossentropy',
        metrics=['accuracy']
    )

    return model

def train_model(model, train_data, validation_data, epochs=10):
    early_stopping = tf.keras.callbacks.EarlyStopping(
        monitor='val_loss', 
        patience=3, 
        restore_best_weights=True
    )

    history = model.fit(
        train_data,
        epochs=epochs,
        validation_data=validation_data,
        callbacks=[early_stopping]
    )

    return history

def plot_training_history(history):
    plt.figure(figsize=(12, 4))
    
    plt.subplot(1, 2, 1)
    plt.plot(history.history['accuracy'], label='Training Accuracy')
    plt.plot(history.history['val_accuracy'], label='Validation Accuracy')
    plt.title('Model Accuracy')
    plt.xlabel('Epoch')
    plt.ylabel('Accuracy')
    plt.legend()

    plt.subplot(1, 2, 2)
    plt.plot(history.history['loss'], label='Training Loss')
    plt.plot(history.history['val_loss'], label='Validation Loss')
    plt.title('Model Loss')
    plt.xlabel('Epoch')
    plt.ylabel('Loss')
    plt.legend()

    plt.tight_layout()
    plt.show()

def main():
    data_dir = 'path/to/cats_and_dogs_dataset'

    train_data, validation_data, test_data = load_data(data_dir)

    model = create_transfer_model()

    # model.summary()

    history = train_model(model, train_data, validation_data)

    plot_training_history(history)

    model.save('cats_vs_dogs_transfer_model.h5')

if __name__ == "__main__":
    main()