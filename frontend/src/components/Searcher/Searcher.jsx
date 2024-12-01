import React, { useState } from "react";

const Searcher = () => {
  const [filters, setFilters] = useState({
    search: "",
    name: "",
    manufacturer: "Cadberry",
    date: "",
    status: "Dispached Out",
  });

  const handleInputChange = (e) => {
    const { id, value } = e.target;
    setFilters((prevFilters) => ({
      ...prevFilters,
      [id]: value,
    }));
  };

  const handleReset = () => {
    setFilters({
      search: "",
      name: "",
      manufacturer: "Cadberry",
      date: "",
      status: "Dispached Out",
    });
  };

  const handleSearch = () => {
    console.log("Search filters:", filters);
    // Aquí puedes conectar con una API o manejar la lógica de búsqueda
  };

  return (
    <div className="m-10 w-screen max-w-screen-md">
      <div className="flex flex-col">
        <div className="rounded-xl border border-gray-200 bg-white p-6 shadow-lg">
          <form>
            {/* Search Input */}
            <div className="relative mb-10 w-full flex items-center justify-between rounded-md">
              <svg
                className="absolute left-2 block h-5 w-5 text-gray-400"
                xmlns="http://www.w3.org/2000/svg"
                width="24"
                height="24"
                viewBox="0 0 24 24"
                fill="none"
                stroke="currentColor"
                strokeWidth="2"
                strokeLinecap="round"
                strokeLinejoin="round"
              >
                <circle cx="11" cy="11" r="8"></circle>
                <line x1="21" y1="21" x2="16.65" y2="16.65"></line>
              </svg>
              <input
                type="text"
                id="search"
                value={filters.search}
                onChange={handleInputChange}
                placeholder="Busca el evento en el que estés interesado"
                className="h-12 w-full cursor-text rounded-md border border-gray-100 bg-gray-100 py-4 pr-40 pl-12 shadow-sm outline-none focus:border-blue-500 focus:ring focus:ring-blue-200 focus:ring-opacity-50"
              />
            </div>

            {/* Filter Inputs */}
            <div className="grid grid-cols-1 gap-6 md:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4">
              <div className="flex flex-col">
                <label htmlFor="name" className="text-sm font-medium text-stone-600">
                  Name
                </label>
                <input
                  type="text"
                  id="name"
                  value={filters.name}
                  onChange={handleInputChange}
                  placeholder="Raspberry juice"
                  className="mt-2 block w-full rounded-md border border-gray-100 bg-gray-100 px-2 py-2 shadow-sm outline-none focus:border-blue-500 focus:ring focus:ring-blue-200 focus:ring-opacity-50"
                />
              </div>

              <div className="flex flex-col">
                <label htmlFor="manufacturer" className="text-sm font-medium text-stone-600">
                  Artistas programados
                </label>
                <select
                  id="manufacturer"
                  value={filters.manufacturer}
                  onChange={handleInputChange}
                  className="mt-2 block w-full rounded-md border border-gray-100 bg-gray-100 px-2 py-2 shadow-sm outline-none focus:border-blue-500 focus:ring focus:ring-blue-200 focus:ring-opacity-50"
                >
                  <option>Cadberry</option>
                  <option>Starbucks</option>
                  <option>Hilti</option>
                </select>
              </div>

              <div className="flex flex-col">
                <label htmlFor="date" className="text-sm font-medium text-stone-600">
                  Date of Entry
                </label>
                <input
                  type="date"
                  id="date"
                  value={filters.date}
                  onChange={handleInputChange}
                  className="mt-2 block w-full cursor-pointer rounded-md border border-gray-100 bg-gray-100 px-2 py-2 shadow-sm outline-none focus:border-blue-500 focus:ring focus:ring-blue-200 focus:ring-opacity-50"
                />
              </div>

              <div className="flex flex-col">
                <label htmlFor="status" className="text-sm font-medium text-stone-600">
                  Status
                </label>
                <select
                  id="status"
                  value={filters.status}
                  onChange={handleInputChange}
                  className="mt-2 block w-full cursor-pointer rounded-md border border-gray-100 bg-gray-100 px-2 py-2 shadow-sm outline-none focus:border-blue-500 focus:ring focus:ring-blue-200 focus:ring-opacity-50"
                >
                  <option>Programado</option>
                  <option>Cancelado</option>
                  <option>Finalizado</option>
                </select>
              </div>
            </div>

            {/* Action Buttons */}
            <div className="mt-6 grid w-full grid-cols-2 justify-end space-x-4 md:flex">
              <button
                type="button"
                onClick={handleReset}
                className="rounded-lg bg-gray-200 px-8 py-2 font-medium text-gray-700 outline-none hover:opacity-80 focus:ring"
              >
                Reset
              </button>
              <button
                type="button"
                onClick={handleSearch}
                className="rounded-lg bg-blue-600 px-8 py-2 font-medium text-white outline-none hover:opacity-80 focus:ring"
              >
                Search
              </button>
            </div>
          </form>
        </div>
      </div>
    </div>
  );
};

export default Searcher;
