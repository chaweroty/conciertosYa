import React from 'react';

const Posters = () => {
  return (
    <div className="text-center p-10">
      <h1 className="font-bold text-4xl mb-4">Tenemos los mejores conciertos para ti</h1>
      <h1 className="text-3xl">Artistas de renombre a nivel mundial</h1>

      {/* Grid Section */}
      <section
        id="Projects"
        className="w-fit mx-auto grid grid-cols-1 lg:grid-cols-3 md:grid-cols-2 justify-items-center justify-center gap-y-20 gap-x-14 mt-10 mb-5"
      >
        {/* Product Card 1 */}
        <div className="w-72 bg-white shadow-md rounded-xl duration-500 hover:scale-105 hover:shadow-xl">
          <a href="#">
            <img
              src="https://elcomercio-depor-prod.web.arc-cdn.net/resizer/v2/SWGJJNBRTFBRXFCOH6I5Y4YH7Y.jpg?auth=1e10e4743cbaf20d3270e46330795e9a27dcada6ade1cb916c4edda812d149d5&width=1200&height=677&smart=true&quality=90"
              alt="Product"
              className="h-80 w-72 object-cover rounded-t-xl"
            />
            <div className="px-4 py-3 w-72">
              <p className="text-lg font-bold text-black truncate block capitalize">Los tigres del norte</p>
              <span className="text-gray-400 mr-3 uppercase text-xs">24 de Diciembre</span>
              <div className="flex items-center">
                <p className="text-lg font-semibold text-black cursor-auto my-3">Pitalito, Huila</p>
                <div className="ml-auto">
                  {/* Reemplazar ícono por botón "Ver más" */}
                  <button className="text-blue-500 font-semibold hover:underline">Ver más</button>
                </div>
              </div>
            </div>
          </a>
        </div>

        {/* Repeat the Product Card for other items */}
        {/* Product Card 2 */}
        {/* Add other cards as needed, following the same structure */}
        
      </section>
    </div>
  );
};

export default Posters;
