
from playwright.sync_api import sync_playwright
import os

def run():
    with sync_playwright() as p:
        browser = p.chromium.launch(headless=True)
        context = browser.new_context()
        page = context.new_page()

        # Load the index.html file
        cwd = os.getcwd()
        page.goto(f"file://{cwd}/index.html")

        # Wait for React to load
        try:
            page.wait_for_selector("#root", timeout=5000)
            # Check if App component rendered (e.g. searching for text "Bibliothek")
            page.wait_for_selector("text=Bibliothek", timeout=10000)

            # Take a screenshot
            page.screenshot(path="verification/app_screenshot.png")
            print("Screenshot taken successfully.")
        except Exception as e:
            print(f"Error: {e}")
            page.screenshot(path="verification/error_screenshot.png")

        browser.close()

if __name__ == "__main__":
    run()
