import React from "react";

const Hero = () => {
  return (
    <div className="relative h-screen w-full">
      <img
        src="https://img.freepik.com/foto-gratis/vista-trasera-gran-grupo-fanaticos-musica-frente-al-escenario-concierto-musica-noche-copiar-espacio_637285-623.jpg?t=st=1731534665~exp=1731538265~hmac=46e91d26b51899d16b022a087df0668389876cb05ad6699a43b13367c541af0c&w=1060"
        alt="Background Image"
        className="absolute inset-0 w-full h-full object-cover filter blur-sm"
      />
      <div className="absolute inset-0 bg-black bg-opacity-50"></div>
      <div className="absolute inset-0 flex flex-col items-center justify-center">
        <h1 className="text-4xl text-white font-bold">Bienvenido a ConciertosYa!</h1>
        <p className="text-xl text-white mt-4">La mejor plataforma para que puedas conocer a los artistas de tus sueños</p>
      </div>
    </div>
  );
};

export default Hero;
