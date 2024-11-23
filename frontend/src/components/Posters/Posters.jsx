import { Link } from 'react-router-dom';

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
          <Link to={`/concert-details/1`}>
            <img
              src="https://upload.wikimedia.org/wikipedia/commons/d/d8/Linkin_Park_-_From_Zero_Lead_Press_Photo_-_James_Minchin_III.jpg"

              alt="Product"
              className="h-80 w-72 object-cover rounded-t-xl"
            />
            <div className="px-4 py-3 w-72">
              <p className="text-lg font-bold text-black truncate block capitalize">Linkin Park</p>
              <span className="text-gray-400 mr-3 uppercase text-xs">Sabado, 9 de noviembre</span>
              <div className="flex items-center">
                <p className="text-lg font-semibold text-black cursor-auto my-3">Bogota, Colombia</p>
                <div className="ml-auto">

                  <button className="text-blue-500 font-semibold hover:underline">Ver más</button>
                </div>
              </div>
            </div>
          </Link>
        </div>

        {/* Agrega más Product Cards según sea necesario */}
      </section>
    </div>
  );
};

export default Posters;
