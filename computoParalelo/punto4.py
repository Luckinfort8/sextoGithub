import asyncio
import pandas as pd
import aiohttp
from datetime import datetime

async def remove_duplicates(input_queue, output_queue):
    while True:
        df = await input_queue.get()
        if df is None:
            await output_queue.put(None)
            break
        df = df.drop_duplicates()
        await output_queue.put(df)

async def get_coordinates(input_queue, output_queue):
    async with aiohttp.ClientSession() as session:
        while True:
            df = await input_queue.get()
            if df is None:
                await output_queue.put(None)
                break
            for i, address in enumerate(df['Address']):
                url = f'https://geocode.xyz/{address}?json=1&auth=973011438326318590362x123055'
                try:
                    async with session.get(url) as response:
                        json_response = await response.json()
                        df.loc[i, 'Latitude'] = json_response['latt']
                        df.loc[i, 'Longitude'] = json_response['longt']
                except Exception as e:
                    print(f"Error obtaining coordinates for {address}: {e}")

            await output_queue.put(df)

async def add_import_timestamp(input_queue):
    while True:
        df = await input_queue.get()
        if df is None:
            break
        df['Imported_Timestamp'] = datetime.now()
        print(df)

async def main():
    queue1 = asyncio.Queue()
    queue2 = asyncio.Queue()
    queue3 = asyncio.Queue()

    dfl = pd.DataFrame({
        'Address': [
            'Av. Hidalgo 21, Centro, Tlaxcala, Tlaxcala',
            'Callejón Ocampo 8, Centro, Tlaxcala, Tlaxcala',
            'Calle Miguel N. Lira 201, San Rafael Tepatlaxco, Tlaxcala, Tlaxcala',
            'Prol. Benito Juárez 104, El Sabinal, Tlaxcala, Tlaxcala',
            'Callejón del Huerto 1, San Sebastián Atlahapa, Tlaxcala, Tlaxcala',
            'Carretera San Francisco Tlacuilohcan - Acuitlapilco, Tlaxcala, Tlaxcala',
            'Calle Francisco Sarabia 32, Santo Toribio Xicohtzinco, Tlaxcala, Tlaxcala',
            'Calle Allende 36, Guadalupe Ixcotla, Tlaxcala, Tlaxcala',
        ]
    })

    await queue1.put(dfl)
    await queue1.put(None)

    # Crear tareas y ejecutarlas
    await asyncio.gather(
        remove_duplicates(queue1, queue2),
        get_coordinates(queue2, queue3),
        add_import_timestamp(queue3)
    )

if __name__ == "__main__":
    asyncio.run(main())
