
const testQuery = async (usePlus) => {
    const title = "Harry Potter";
    const author = "Rowling";

    // Logic from the app
    const q = usePlus
        ? `intitle:${title}+inauthor:${author}`
        : `intitle:${title} inauthor:${author}`;

    const encodedQ = encodeURIComponent(q);
    const url = `https://www.googleapis.com/books/v1/volumes?q=${encodedQ}&maxResults=1`;

    console.log(`Testing with usePlus=${usePlus}`);
    console.log(`Raw q: ${q}`);
    console.log(`Encoded q: ${encodedQ}`);

    try {
        const res = await fetch(url);
        const data = await res.json();
        console.log(`Response status: ${res.status}`);
        console.log(`Data:`, JSON.stringify(data, null, 2));
    } catch (e) {
        console.error("Error:", e.message);
    }
    console.log("--------------------------------");
};

(async () => {
    await testQuery(true);  // With +
    await testQuery(false); // With space
})();
