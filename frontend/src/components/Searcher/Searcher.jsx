import React, { useState } from "react";

const Searcher = () => {
  const [keyword, setKeyword] = useState("");

  const handleSearch = (e) => {
    e.preventDefault();
    if (!keyword) return;
    console.log(`Buscando: ${keyword}`);
    // Aquí podrías añadir lógica para realizar la búsqueda.
    setKeyword("");
  };

  return (
    <form
      onSubmit={handleSearch}
      className="mt-10 mx-auto max-w-xl py-2 px-6 rounded-full bg-gray-50 border flex focus-within:border-gray-300"
    >
      <input
        type="text"
        placeholder="Search anything"
        value={keyword}
        onChange={(e) => setKeyword(e.target.value)}
        className="bg-transparent w-full focus:outline-none pr-4 font-semibold border-0 focus:ring-0 px-0 py-0"
        name="topic"
      />
      <button
        type="submit"
        className="flex flex-row items-center justify-center min-w-[130px] px-4 rounded-full font-medium tracking-wide border disabled:cursor-not-allowed disabled:opacity-50 transition ease-in-out duration-150 text-base bg-black text-white font-medium tracking-wide border-transparent py-1.5 h-[38px] -mr-3"
        disabled={!keyword}
      >
        Search
      </button>
    </form>
  );
};

export default Searcher;
