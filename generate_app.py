import os

def create_dirs(base_path, dirs):
    for d in dirs:
        path = os.path.join(base_path, d)
        os.makedirs(path, exist_ok=True)
        print(f"Created: {path}")

def main():
    base = "VideoDownloader"

    dirs = [
        "",
        "app",
        "app/src/main",
        "app/src/main/java/com/example/videodownloader",
        "app/src/main/java/com/example/videodownloader/domain/model",
        "app/src/main/java/com/example/videodownloader/domain/repository",
        "app/src/main/java/com/example/videodownloader/data/local",
        "app/src/main/java/com/example/videodownloader/data/repository",
        "app/src/main/java/com/example/videodownloader/presentation/ui/theme",
        "app/src/main/java/com/example/videodownloader/presentation/viewmodel",
        "app/src/main/java/com/example/videodownloader/presentation/screen",
        "app/src/main/java/com/example/videodownloader/di",
        "app/src/main/res/values",
        "app/src/main/res/drawable",
        "app/src/main/res/mipmap-anydpi-v26",
    ]

    create_dirs(base, dirs)

if __name__ == "__main__":
    main()
