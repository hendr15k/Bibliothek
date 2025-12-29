
from playwright.sync_api import sync_playwright
import os

def run():
    with sync_playwright() as p:
        browser = p.chromium.launch(headless=True)
        context = browser.new_context()
        page = context.new_page()

        # Subscribe to console events
        page.on("console", lambda msg: print(f"BROWSER CONSOLE: {msg.type}: {msg.text}"))
        page.on("pageerror", lambda exc: print(f"BROWSER ERROR: {exc}"))

        # Load the index.html file
        cwd = os.getcwd()
        page.goto(f"file://{cwd}/index.html")

        try:
            # Wait for 5 seconds to see logs
            page.wait_for_timeout(5000)
            page.wait_for_selector("#root", timeout=1000)
        except Exception as e:
            print(f"Script Error: {e}")

        browser.close()

if __name__ == "__main__":
    run()
